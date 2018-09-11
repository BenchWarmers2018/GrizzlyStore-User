package com.benchwarmers.grads.grizzlystoreuser;

import com.benchwarmers.grads.grizzlystoreuser.entities.Account;
import com.benchwarmers.grads.grizzlystoreuser.repositories.Account_Repository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.time.Instant;
import java.util.Date;
import java.util.UUID;

public class GrizzlystoreUserAccountTests {
    @Mock
    private Account_Repository mockedAccountRepository;

    @Mock
    private Account testAccount;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        testAccount = new Account();
        testAccount.setIdAccount(UUID.randomUUID());
        testAccount.setAccountEmailAddress("Anto@abc.com");
        testAccount.setAccountPassword("1234");
        testAccount.setAdminStatus(false);
        testAccount.setLastModified(Date.from(Instant.now()));
        Mockito.when(mockedAccountRepository.save( Mockito.any(Account.class) )).thenReturn(testAccount);
    }

    @Test
    public void saveAccount() {
        Account testAccount2 = mockedAccountRepository.save(new Account());
        Assert.assertNotNull(testAccount2);
    }

    @Test
    public void AccountIsNotAdmin() {
        Account newAccount = mockedAccountRepository.save(new Account());
        Assert.assertEquals(newAccount.isAdmin(), false);
    }

    @Test
    public void AccountIsAdmin() {
        Account newAccount = mockedAccountRepository.save(new Account());
        newAccount.setAdminStatus(true);
        Assert.assertEquals(newAccount.isAdmin(), true);
    }

    @Test
    public void AccountHasCorrectEmailAddress() {
        Account newAccount = mockedAccountRepository.save(new Account());
        Assert.assertEquals("Anto@abc.com", newAccount.getAccountEmailAddress());
    }

    @Test
    public void AccountHasIncorrectEmailAddress() {
        Account newAccount = mockedAccountRepository.save(new Account());
        newAccount.setAccountEmailAddress("Anto@xyz.com");
        Assert.assertNotEquals("Anto@abc.com", newAccount.getAccountEmailAddress());
    }

    @Test
    public void AccountHasUUID() {
        Account newAccount = mockedAccountRepository.save(new Account());
        Assert.assertNotNull(newAccount.getIdAccount());
    }

    @Test
    public void AccountHasLastModifiedDatetime() {
        Account newAccount = mockedAccountRepository.save(new Account());
        Assert.assertNotNull(newAccount.getLastModified());
    }
}