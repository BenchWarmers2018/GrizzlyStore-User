package com.benchwarmers.grads.grizzlystoreuser;

import com.benchwarmers.grads.grizzlystoreuser.controllers.LoginController;
import com.benchwarmers.grads.grizzlystoreuser.entities.Account;
import com.benchwarmers.grads.grizzlystoreuser.repositories.Account_Repository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.annotation.Resource;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class GrizzlystoreUserLoginControllerTests {
    @Resource
    private Account_Repository accountRepository;

    @Autowired
    private LoginController loginController;

    @Autowired
    private MockMvc mockMvc;

    private String testJson;

    private Account testAccount;

    @Before
    public void setup() {
        accountRepository.deleteAll();
        testAccount = new Account();
        testAccount.setAccountEmailAddress("Anto@abc.com");
        testAccount.setAccountPassword("password");
        testAccount.setAdminStatus(true);
        accountRepository.save(testAccount);

        mockMvc = MockMvcBuilders.standaloneSetup(loginController).build();
    }

    @Test
    public void accountSaveSuccess() {
        Account testAccount2 = accountRepository.findAccountByAccountEmailAddress("anto@abc.com");
        Assert.assertEquals("anto@abc.com", testAccount2.getAccountEmailAddress());
    }

    @Test
    public void accountLoginSuccess() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        testJson = "{" +
                "\"accountEmailAddress\":\"anto@abc.com\", " +
                "\"accountPassword\":\"password\"" +
                "}";

        // POST the Json variable containing an email and password to the login controller
        // and expect an OK response and Json data in return
        MvcResult result = mockMvc.perform(
                MockMvcRequestBuilders.post("/login/authenticate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(testJson))
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();

        // Create a JSONArray using the array within the JsonResponse called "entities"
        JSONArray jsonEntities = new JSONObject(content).getJSONArray("entities");

        // Create a JSON Object containing the testAccount
        JSONObject account = new JSONObject(mapper.writeValueAsString(testAccount));

        // Checks that the first index within the entities contains the account we logged in as (testAccount)
        JSONAssert.assertEquals(account.toString(), jsonEntities.get(0).toString(), true);
    }

    @Test
    public void accountLoginIncorrectPassword() throws Exception {
        testJson = "{" +
                "\"accountEmailAddress\":\"anto@abc.com\", " +
                "\"accountPassword\":\"password123\"" +
                "}";

        MvcResult result = mockMvc.perform(
                MockMvcRequestBuilders.post("/login/authenticate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(testJson))
                .andExpect(status().isNotAcceptable())
                .andReturn();

        String content = result.getResponse().getContentAsString();

        // Get the objects within the "errors" section of the Json Response
        JSONArray jsonErrors = new JSONObject(content).getJSONArray("errors");

        // Expected error message
        String errorMessage = "Email address or password is incorrect!";

        // Error message within the JsonResponse
        String jsonErrorMessage = jsonErrors.get(0).toString();

        Assert.assertEquals(errorMessage, jsonErrorMessage);
    }

    @Test
    public void accountLoginEmailIsCaseInsensitive() throws Exception {
        testJson = "{" +
                "\"accountEmailAddress\":\"AnTo@AbC.CoM\", " +
                "\"accountPassword\":\"password\"" +
                "}";

        // Tests that the email address entered does not have to match casing
        // Must return an ok status
        mockMvc.perform(
                MockMvcRequestBuilders.post("/login/authenticate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(testJson))
                .andExpect(status().isOk());
    }

    @Test
    public void accountLoginNoEmailAddressEntered() throws Exception {
        testJson = "{" +
                "\"accountEmailAddress\":\"\", " +
                "\"accountPassword\":\"password\"" +
                "}";

        MvcResult result = mockMvc.perform(
                MockMvcRequestBuilders.post("/login/authenticate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(testJson))
                .andExpect(status().isNotAcceptable())
                .andReturn();

        String content = result.getResponse().getContentAsString();

        // Get the objects within the "Errors" section of the Json Response
        JSONArray jsonErrors = new JSONObject(content).getJSONArray("errors");

        // Expected error message
        String errorMessage = "An email address and a password must be entered!";

        // Error message within the JsonResponse
        String jsonErrorMessage = jsonErrors.get(0).toString();

        Assert.assertEquals(jsonErrorMessage, errorMessage);
    }

    @Test
    public void accountLoginNoPasswordEntered() throws Exception {
        testJson = "{" +
                "\"accountEmailAddress\":\"anto@abc.com\", " +
                "\"accountPassword\":\"\"" +
                "}";

        MvcResult result = mockMvc.perform(
                MockMvcRequestBuilders.post("/login/authenticate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(testJson))
                .andExpect(status().isNotAcceptable())
                .andReturn();

        String content = result.getResponse().getContentAsString();

        JSONArray jsonErrors = new JSONObject(content).getJSONArray("errors");

        String errorMessage = "An email address and a password must be entered!";

        String jsonErrorMessage = jsonErrors.get(0).toString();

        Assert.assertEquals(errorMessage, jsonErrorMessage);
    }

    @Test
    public void accountEmailDoesNotExist() throws Exception {
        testJson = "{" +
                "\"accountEmailAddress\":\"anthony@abc.com\", " +
                "\"accountPassword\":\"password\"" +
                "}";

        MvcResult result = mockMvc.perform(
                MockMvcRequestBuilders.post("/login/authenticate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(testJson))
                .andExpect(status().isNotAcceptable())
                .andReturn();

        String content = result.getResponse().getContentAsString();

        JSONArray jsonErrors = new JSONObject(content).getJSONArray("errors");

        String errorMessage = "An account does not exist with the email address: anthony@abc.com";

        String jsonErrorMessage = jsonErrors.get(0).toString();

        Assert.assertEquals(errorMessage, jsonErrorMessage);
    }
}
