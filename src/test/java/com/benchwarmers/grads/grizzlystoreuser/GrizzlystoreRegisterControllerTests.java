package com.benchwarmers.grads.grizzlystoreuser;

import com.benchwarmers.grads.grizzlystoreuser.controllers.RegisterController;
import com.benchwarmers.grads.grizzlystoreuser.entities.Account;
import com.benchwarmers.grads.grizzlystoreuser.repositories.Account_Repository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.annotation.Resource;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class GrizzlystoreRegisterControllerTests
{
    @Resource
    private Account_Repository mockedAccountRepository;


    private Account testAccount;

    @Autowired
    private RegisterController registerController;

    @Autowired
    MockMvc mvc;

    @Before
    public void setup() {
        mockedAccountRepository.deleteAll();
        testAccount = new Account();
        testAccount.setAccountEmailAddress("Hello@abc.com");
        testAccount.setAccountPassword("12345");
        testAccount.setAdminStatus(false);
        mockedAccountRepository.save(testAccount);

        this.mvc = MockMvcBuilders.standaloneSetup(registerController).build();
    }
    @Test
    public void createUserSuccess() throws Exception
    {
        mvc.perform(post("/register/create").contentType(MediaType.APPLICATION_JSON).content("{\t\n" +
                "\t\t\"accountEmailAddress\":\"p2qq@2ssniew.sdej\",\n" +
                "\t\t\"accountPassword\":\"helwerewrewrlooo\",\n" +
                "\t\t\"accountIsAdmin\":\"\"\n" +
                "\t}")).andExpect(status().isOk());
    }

    @Test
    public void UserSameEmailTest() throws Exception
    {
        mvc.perform(post("/register/create").contentType(MediaType.APPLICATION_JSON).content("{\t\n" +
                        "\t\t\"accountEmailAddress\":\"Hello@abc.com\",\n" +
                        "\t\t\"accountPassword\":\"helwerewrewrlooo\",\n" +
                        "\t\t\"accountIsAdmin\":\"\"\n" +
                        "\t}"
                )).andExpect(status().isBadRequest());
    }
    @Test
    public void invalidEmailaddress() throws Exception
    {
        mvc.perform(post("/register/create").contentType(MediaType.APPLICATION_JSON).content("{\t\n" +
                "\t\t\"accountEmailAddress\":\"p2qq2ssniew.sdej\",\n" +
                "\t\t\"accountPassword\":\"helwerewrewrlooo\",\n" +
                "\t\t\"accountIsAdmin\":\"\"\n" +
                "\t}")).andExpect(status().isBadRequest());
    }

    @Test
    public void ValidEmailaddress() throws Exception
    {
        mvc.perform(post("/register/create").contentType(MediaType.APPLICATION_JSON).content("{\t\n" +
                "\t\t\"accountEmailAddress\":\"Iamvalid@valid.com\",\n" +
                "\t\t\"accountPassword\":\"helwerewrewrlooo\",\n" +
                "\t\t\"accountIsAdmin\":\"\"\n" +
                "\t}")).andExpect(status().isOk());
    }
}
