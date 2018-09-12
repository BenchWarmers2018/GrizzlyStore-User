package com.benchwarmers.grads.grizzlystoreuser;

import com.benchwarmers.grads.grizzlystoreuser.controllers.LoginController;
import com.benchwarmers.grads.grizzlystoreuser.entities.Account;
import com.benchwarmers.grads.grizzlystoreuser.repositories.Account_Repository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.Instant;
import java.util.Date;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@DataJpaTest
public class GrizzlystoreUserLoginControllerTests {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private Account_Repository mockedAccountRepository;

    private Account testAccount;

    @InjectMocks
    private LoginController loginController;

//    @Autowired
    MockMvc mvc;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        testAccount = new Account();
        testAccount.setIdAccount(UUID.randomUUID());
        testAccount.setAccountEmailAddress("Anto@abc.com");
        testAccount.setAccountPassword("password");
        testAccount.setAdminStatus(true);
        testAccount.setLastModified(Date.from(Instant.now()));
        entityManager.persist(testAccount);
        entityManager.flush();
        Mockito.when(mockedAccountRepository.save( Mockito.any(Account.class) )).thenReturn(testAccount);
        this.mvc = MockMvcBuilders
                .standaloneSetup(loginController).build();
    }

//    @Test
//    public void LoginSuccessful() throws Exception {
//        mockedAccountRepository.save(testAccount);
//        mvc.perform(post("/login/authenticate").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON).content(
//            "{" +
//                "\"accountEmailAddress\":\"Anto@abc.com\", " +
//                "\"accountPassword\":\"password\"," +
//            "}"
//            )
//        ).andExpect(status().isOk());
//    }
}
