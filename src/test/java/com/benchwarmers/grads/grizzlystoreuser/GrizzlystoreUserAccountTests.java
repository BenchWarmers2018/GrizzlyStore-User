package com.benchwarmers.grads.grizzlystoreuser;

import com.benchwarmers.grads.grizzlystoreuser.entities.Account;
import com.benchwarmers.grads.grizzlystoreuser.repositories.Account_Repository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
    public void accountIsNotAdmin() {
        Account newAccount = mockedAccountRepository.save(new Account());
        Assert.assertEquals(newAccount.isAdmin(), false);
    }

    @Test
    public void accountIsAdmin() {
        Account newAccount = mockedAccountRepository.save(new Account());
        newAccount.setAdminStatus(true);
        Assert.assertEquals(newAccount.isAdmin(), true);
    }

    @Test
    public void accountHasCorrectEmailAddress() {
        Account newAccount = mockedAccountRepository.save(new Account());
        Assert.assertEquals("anto@abc.com", newAccount.getAccountEmailAddress());
    }

    @Test
    public void accountHasIncorrectEmailAddress() {
        Account newAccount = mockedAccountRepository.save(new Account());
        newAccount.setAccountEmailAddress("Anto@xyz.com");
        Assert.assertNotEquals("Anto@abc.com", newAccount.getAccountEmailAddress());
    }

    @Test
    public void accountHasUUID() {
        Account newAccount = mockedAccountRepository.save(new Account());
        Assert.assertNotNull(newAccount.getIdAccount());
    }

    @Test
    public void accountHasLastModifiedDatetime() {
        Account newAccount = mockedAccountRepository.save(new Account());
        Assert.assertNotNull(newAccount.getLastModified());
    }

    @Test
    public void createAccountJsonResponse() {
        List<Data> listOfAccounts = new ArrayList<>();
        listOfAccounts.add(testAccount);
        listOfAccounts.add(testAccount);
        listOfAccounts.add(testAccount);

        List<String> errors = new ArrayList<>();
        errors.add("Error message 1");
        errors.add("Error message 2");

        JsonResponse response = new JsonResponse(HttpStatus.ACCEPTED, listOfAccounts, errors);
        Assert.assertEquals(response.getEntities().size(), 3);
        Assert.assertEquals(response.getErrors().size(), 2);
        Assert.assertEquals(response.getStatus(), HttpStatus.ACCEPTED);
    }
}