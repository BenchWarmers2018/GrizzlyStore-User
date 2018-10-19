package com.benchwarmers.grads.grizzlystoreuser.controllers;

import com.benchwarmers.grads.grizzlystoreuser.Data;
import com.benchwarmers.grads.grizzlystoreuser.JsonResponse;
import com.benchwarmers.grads.grizzlystoreuser.entities.Account;
import com.benchwarmers.grads.grizzlystoreuser.repositories.Account_Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping(path = "/account")
@CrossOrigin
public class AccountController {
    @Autowired
    Account_Repository accountRepository;

    //This allows the controller to return all the accounts
    @RequestMapping(path = "/all")
    public ResponseEntity getAllAccounts()
    {
        JsonResponse response = new JsonResponse();

        //This List is used to replace the items list in each category
        List<Account> accounts = accountRepository.findAll();
        List<Data> accountData = new ArrayList<>();

        for(Account i : accounts)
            accountData.add(i);

        response.setStatus(HttpStatus.OK);
        response.addAllEntities(accountData);
        return response.createResponse();
    }

    // Toggles the account's admin status
    @RequestMapping(path = "/toggleStatus", method = POST)
    public ResponseEntity toggleAdminStatus(@RequestParam("idAccount") String idAccount)
    {
        JsonResponse response = new JsonResponse();

        Account userAccount = accountRepository.findByIdAccount(UUID.fromString(idAccount));

        if (userAccount == null)
        {
            createErrorMessage(response, "This user account does not exist!");
        }
        else
        {
            // Toggle admin status
            userAccount.setAccountIsAdmin(!userAccount.isAccountIsAdmin());
            accountRepository.save(userAccount);

            response.setStatus(HttpStatus.OK);
            response.addEntity(userAccount);
        }

        return response.createResponse();
    }

    private void createErrorMessage(JsonResponse response, String string) {
        response.setStatus(HttpStatus.NOT_ACCEPTABLE);
        response.addErrorMessage(string);
    }
}
