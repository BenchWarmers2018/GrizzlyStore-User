package com.benchwarmers.grads.grizzlystoreuser;

import com.benchwarmers.grads.grizzlystoreuser.controllers.UserProfileController;
import com.benchwarmers.grads.grizzlystoreuser.entities.*;
import com.benchwarmers.grads.grizzlystoreuser.repositories.Account_Repository;
import com.benchwarmers.grads.grizzlystoreuser.repositories.Address_Repository;
import com.benchwarmers.grads.grizzlystoreuser.repositories.Profile_Repository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONException;
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

    @Mock
    private Address_Repository mockedAddressRepository;

    @InjectMocks
    private UserProfileController userProfileController;

    @Mock
    private Account testAccount1; // Account with no profile

    @Mock
    private Account testAccount2; // Account with a profile

    @Mock
    private JSONObject accountObjectValid;

    @Mock
    private JSONObject accountObjectInvalid;

    @Mock
    private Profile testProfile;

    @Mock
    private Address testAddress;

    @Autowired
    private UUID invalidUUID;

    @Autowired
    private MockMvc mvc;

    @Before
    public void setup() throws JSONException {
        MockitoAnnotations.initMocks(this);
        this.mvc = MockMvcBuilders.standaloneSetup(userProfileController).build();
        setupAccounts();
        setupProfiles();
        //setupAddress();
        testAccount2.setProfile(testProfile);
        testProfile.setAddress(null);
        invalidUUID = UUID.randomUUID();
        Mockito.when(mockedAccountRepository.findByIdAccount(testAccount2.getIdAccount())).thenReturn(testAccount2);
        Mockito.when(mockedAccountRepository.findByIdAccount(testAccount1.getIdAccount())).thenReturn(testAccount1);
        Mockito.when(mockedProfileRepository.save(Mockito.any(Profile.class))).thenReturn(testProfile);
        Mockito.when(mockedProfileRepository.findByUserAccount(null)).thenReturn(null);
        Mockito.when(mockedAccountRepository.findByIdAccount(invalidUUID)).thenReturn(null);

        accountObjectValid = new JSONObject();
        accountObjectValid.put("idAccount", testAccount2.getIdAccount().toString());
        accountObjectValid.put("accountEmailAddress", "abc@gmail.com");
        accountObjectValid.put("accountPassword", "abcd");
        accountObjectValid.put("accountIsAdmin", "true");

        accountObjectInvalid = new JSONObject();
        accountObjectInvalid.put("idAccount", invalidUUID.toString());
        accountObjectInvalid.put("accountEmailAddress", "abc@gmail.com");
        accountObjectInvalid.put("accountPassword", "abcd");
        accountObjectInvalid.put("accountIsAdmin", "true");
    }

    public void setupAccounts() { // Setup account objects
        // Account with no profile
        testAccount1 = new Account();
        testAccount1.setIdAccount(UUID.randomUUID());
        testAccount1.setAccountEmailAddress("Anto@abc.com");
        testAccount1.setAccountPassword("1234");
        testAccount1.setAccountIsAdmin(false);
        testAccount1.setProfile(null);
        testAccount1.setLastModified(Date.from(Instant.now()));

        // Account with a profile
        testAccount2 = new Account();
        testAccount2.setIdAccount(UUID.randomUUID());
        testAccount2.setAccountEmailAddress("paarth@abc.com");
        testAccount2.setAccountPassword("1234");
        testAccount2.setAccountIsAdmin(false);
        testAccount2.setLastModified(Date.from(Instant.now()));
        Mockito.when(mockedAccountRepository.save(Mockito.any(Account.class))).thenReturn(testAccount2);
    }

    public void setupProfiles() { // Setup a profile object
        testProfile = new Profile();
        testProfile.setProfileFirstName("Paarth");
        testProfile.setProfileLastName("B");
        testProfile.setProfilePhoneNumber("045555555");
        testProfile.setProfileImage("ABCDEFGH");
        testProfile.setIdProfile(1);
        testProfile.setLastModified(Date.from(Instant.now()));
        Mockito.when(mockedProfileRepository.save(Mockito.any(Profile.class))).thenReturn(testProfile);
    }

    private void setupAddress() {
        testAddress = new Address();
        testAddress.setIdAddress(1);
        testAddress.setAddressStreet("123 Fake Street");
        testAddress.setAddressStreetType("N/A");
        testAddress.setAddressStreetNo("N/A");
        testAddress.setAddressCity("Melbourne");
        testAddress.setAddressState("VIC");
        testAddress.setAddressPostcode("1234");
        testAddress.setAddressUnitNo("2");
        testAddress.setAddressCountry("Australia");
        testAddress.setLastModified(Date.from(Instant.now()));
        Mockito.when(mockedAddressRepository.save(Mockito.any(Address.class))).thenReturn(testAddress);
    }

//    @Test
//    public void getUserProfileValid() throws Exception {
//        ObjectMapper mapper = new ObjectMapper();
//        Account newAccount = mockedAccountRepository.save(new Account());
//        Profile newProfile = mockedProfileRepository.save(new Profile());
//        MvcResult result = mvc.perform(
//                MockMvcRequestBuilders.post("/user/profile")
//                        .accept(MediaType.ALL)
//                        .param("accountID", newAccount.getIdAccount().toString())
//                        .contentType(MediaType.ALL))
//                .andExpect(status().isOk()).andReturn();
//        String content = result.getResponse().getContentAsString();
//        JSONObject jsonResponse = new JSONObject(content).getJSONArray("entities").getJSONObject(0).getJSONObject("profile");
//        JSONObject profile = new JSONObject(mapper.writeValueAsString(newProfile));
//        JSONAssert.assertEquals(profile, jsonResponse, true);
//    }
//
//    @Test
//    public void getUserProfileInvalid() throws Exception {
//        // Non-existent account with invalid account ID
//        MvcResult result = mvc.perform(
//                MockMvcRequestBuilders.post("/user/profile")
//                        .accept(MediaType.ALL)
//                        .param("accountID", invalidUUID.toString())
//                        .contentType(MediaType.ALL))
//                .andExpect(status().isNotAcceptable()).andReturn();
//        String content = result.getResponse().getContentAsString();
//        JSONArray jsonResponse = new JSONObject(content).getJSONArray("errors");
//        Assert.assertEquals("Account ID " + invalidUUID.toString() + " doesn't exist.",
//                jsonResponse.get(0).toString());
//
//        // Account with no profile
//        result = mvc.perform(
//                MockMvcRequestBuilders.post("/user/profile")
//                        .accept(MediaType.ALL)
//                        .param("accountID", testAccount1.getIdAccount().toString())
//                        .contentType(MediaType.ALL))
//                .andExpect(status().isNotAcceptable()).andReturn();
//        content = result.getResponse().getContentAsString();
//        jsonResponse = new JSONObject(content).getJSONArray("errors");
//        Assert.assertEquals("Account has no profile.",
//                jsonResponse.get(0).toString());
//    }
//
//    @Test
//    public void getUserProfileInvalidObject() throws Exception {
//        // Non-existent account with invalid account ID
//        MvcResult result = mvc.perform(
//                MockMvcRequestBuilders.post("/user/profile-account")
//                        .accept(MediaType.ALL)
//                        .content(accountObjectInvalid.toString())
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isNotAcceptable()).andReturn();
//        String content = result.getResponse().getContentAsString();
//        JSONArray jsonResponse = new JSONObject(content).getJSONArray("errors");
//        Assert.assertEquals("Account ID " + invalidUUID.toString() + " doesn't exist.",
//                jsonResponse.get(0).toString());
//
//        // Account with no profile
//        accountObjectInvalid.put("idAccount", testAccount1.getIdAccount().toString());
//        result = mvc.perform(
//                MockMvcRequestBuilders.post("/user/profile-account")
//                        .accept(MediaType.ALL)
//                        .content(accountObjectInvalid.toString())
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isNotAcceptable()).andReturn();
//        content = result.getResponse().getContentAsString();
//        jsonResponse = new JSONObject(content).getJSONArray("errors");
//        Assert.assertEquals("Account has no profile.",
//                jsonResponse.get(0).toString());
//    }

//    @Test
//    public void getUserProfileValidObject() throws Exception {
//        ObjectMapper mapper = new ObjectMapper();
//        Profile newProfile = mockedProfileRepository.save(new Profile());
//        MvcResult result = mvc.perform(
//                MockMvcRequestBuilders.post("/user/profile-account")
//                        .accept(MediaType.ALL)
//                        .content(accountObjectValid.toString())
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk()).andReturn();
//        String content = result.getResponse().getContentAsString();
//        System.out.println(content);
//        JSONObject jsonResponse = new JSONObject(content).getJSONArray("entities").getJSONObject(0);
//        JSONObject profile = new JSONObject(mapper.writeValueAsString(newProfile));
//        JSONAssert.assertEquals(profile, jsonResponse, true);
//    }
}