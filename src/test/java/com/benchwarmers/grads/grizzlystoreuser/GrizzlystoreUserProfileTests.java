package com.benchwarmers.grads.grizzlystoreuser;

import com.benchwarmers.grads.grizzlystoreuser.controllers.UserProfileController;
import com.benchwarmers.grads.grizzlystoreuser.entities.*;
import com.benchwarmers.grads.grizzlystoreuser.repositories.Account_Repository;
import com.benchwarmers.grads.grizzlystoreuser.repositories.Profile_Repository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.Instant;
import java.util.Date;
import java.util.UUID;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class GrizzlystoreUserProfileTests {
    @Mock
    private Account_Repository mockedAccountRepository;

    @Mock
    private Profile_Repository mockedProfileRepository;

    @InjectMocks
    private UserProfileController userProfileController;

    @Mock
    private Account testAccount;

    @Mock
    private Profile testProfile;

    @Autowired
    private UUID invalidUUID;

    @Autowired
    private MockMvc mvc;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        this.mvc = MockMvcBuilders.standaloneSetup(userProfileController).build();
        testAccount = new Account();
        testAccount.setIdAccount(UUID.randomUUID());
        invalidUUID = UUID.randomUUID();
        testAccount.setAccountEmailAddress("Anto@abc.com");
        testAccount.setAccountPassword("1234");
        testAccount.setAdminStatus(false);
        testAccount.setLastModified(Date.from(Instant.now()));
        Mockito.when(mockedAccountRepository.save(Mockito.any(Account.class))).thenReturn(testAccount);
        testProfile = new Profile();
        testProfile.setProfileFirstName("Paarth");
        testProfile.setProfileLastName("B");
        testProfile.setProfilePhoneNumber("045555555");
        testProfile.setProfileImage("ABCDEFGH");
        testProfile.setUserAccount(mockedAccountRepository.save(new Account()));
        testProfile.setIdProfile(1);
        testProfile.setLastModified(Date.from(Instant.now()));
        Mockito.when(mockedProfileRepository.findByUserAccount(testAccount.getIdAccount())).thenReturn(testProfile);
        Mockito.when(mockedProfileRepository.save(Mockito.any(Profile.class))).thenReturn(testProfile);
        Mockito.when(mockedProfileRepository.findByUserAccount(invalidUUID)).thenReturn(null);
    }

    @Test
    public void getUserProfileValid() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        Account newAccount = mockedAccountRepository.save(new Account());
        Profile newProfile = mockedProfileRepository.save(new Profile());
        MvcResult result = mvc.perform(
                MockMvcRequestBuilders.post("/user/profile")
                        .accept(MediaType.ALL)
                        .param("accountID", newAccount.getIdAccount().toString())
                        .contentType(MediaType.ALL))
                .andExpect(status().isOk()).andReturn();
        String content = result.getResponse().getContentAsString();
        JSONObject jsonResponse = new JSONObject(content).getJSONObject("Entities");
        JSONObject profile = new JSONObject(mapper.writeValueAsString(newProfile));
        JSONAssert.assertEquals(profile, (JSONObject) jsonResponse.get("1"), true);
    }

    @Test
    public void getUserProfileInvalid() throws Exception {
        MvcResult result = mvc.perform(
                MockMvcRequestBuilders.post("/user/profile")
                        .accept(MediaType.ALL)
                        .param("accountID", invalidUUID.toString())
                        .contentType(MediaType.ALL))
                .andExpect(status().isNotAcceptable()).andReturn();
        String content = result.getResponse().getContentAsString();
        System.out.println(result + "\n" + content);
        JSONObject jsonResponse = new JSONObject(content).getJSONObject("Errors");
        Assert.assertEquals("Account ID " + invalidUUID.toString() + " doesn't exist.",
                jsonResponse.get("1").toString());
    }
}
