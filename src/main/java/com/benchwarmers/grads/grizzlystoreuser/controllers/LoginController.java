package com.benchwarmers.grads.grizzlystoreuser.controllers;

import com.benchwarmers.grads.grizzlystoreuser.JsonResponse;
import com.benchwarmers.grads.grizzlystoreuser.entities.Account;
import com.benchwarmers.grads.grizzlystoreuser.repositories.Account_Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping(path = "/login")
public class LoginController {
    @Autowired
    private Account_Repository accRepo;

    @PostMapping(path = "/authenticate", consumes = "application/json", produces = "application/json")
    public ResponseEntity login (@RequestBody Account account) {
        String enteredEmailAddress = account.getAccountEmailAddress().toLowerCase();
        String enteredPassword = account.getAccountPassword();
        JsonResponse response = new JsonResponse();

        if (!isNullOrEmpty(enteredEmailAddress) && !isNullOrEmpty(enteredPassword))
        {
            Account existingAccount = getExistingAccount(enteredEmailAddress);

            if (existingAccount == null)
            {
                createErrorMessage(response, "An account does not exist with the email address: " + enteredEmailAddress);
            }
            else if ( passwordMatches(existingAccount, enteredPassword) )
            {
                response.setStatus(HttpStatus.OK);
                response.addEntity(existingAccount);
            }
            else
            {
                createErrorMessage(response, "Email address or password is incorrect!");
            }
        }
        else
        {
            System.out.println(enteredEmailAddress);
            System.out.println(enteredPassword);
            createErrorMessage(response,"An email address and a password must be entered!");
        }

        return response.createResponse();
    }

    // Checks whether input is null and if it is empty
    private Boolean isNullOrEmpty(String input) {
        return (input.isEmpty() || input.equals(null));
    }

    private Account getExistingAccount(String emailAddress) {
        return accRepo.findAccountByAccountEmailAddress(emailAddress);
    }

    private Boolean passwordMatches(Account existingAccount, String enteredPassword) {
        return existingAccount.getAccountPassword().equals(enteredPassword);
    }

    private void createErrorMessage(JsonResponse response, String string) {
        response.setStatus(HttpStatus.NOT_ACCEPTABLE);
        response.addErrorMessage(string);
    }
}