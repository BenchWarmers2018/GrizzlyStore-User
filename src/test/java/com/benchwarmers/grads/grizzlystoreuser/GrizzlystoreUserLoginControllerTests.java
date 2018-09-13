package com.benchwarmers.grads.grizzlystoreuser;

import com.benchwarmers.grads.grizzlystoreuser.controllers.LoginController;
import com.benchwarmers.grads.grizzlystoreuser.entities.Account;
import com.benchwarmers.grads.grizzlystoreuser.repositories.Account_Repository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.annotation.Resource;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class GrizzlystoreUserLoginControllerTests {
    @Resource
    private Account_Repository accountRepository;

    @Autowired
    private LoginController loginController;

    @Autowired
    private MockMvc mockMvc;

    private String testJson;

    @Before
    public void setup() {
        Account testAccount = new Account();
        testAccount.setIdAccount(UUID.randomUUID());
        testAccount.setAccountEmailAddress("Anto@abc.com");
        testAccount.setAccountPassword("password");
        testAccount.setAdminStatus(true);
        testAccount.setLastModified(Date.from(Instant.now()));

        accountRepository.save(testAccount);

        testJson = "{" +
                        "\"accountEmailAddress\":\"Anto@abc.com\", " +
                        "\"accountPassword\":\"password\"" +
                   "}";

        mockMvc = MockMvcBuilders.standaloneSetup(loginController).build();
    }

    @Test
    public void accountSaveSuccess() {
        Account testAccount2 = accountRepository.findAccountByAccountEmailAddress("Anto@abc.com");
        Assert.assertEquals("Anto@abc.com", testAccount2.getAccountEmailAddress());
    }

    @Test
    public void accountLoginSuccess() throws Exception {
        Account testAccount = accountRepository.findAccountByAccountEmailAddress("Anto@abc.com");
        System.out.println("test");
        mockMvc.perform(
                MockMvcRequestBuilders.post("/login/authenticate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(testJson))
                .andExpect(status().isOk());
    }
}
