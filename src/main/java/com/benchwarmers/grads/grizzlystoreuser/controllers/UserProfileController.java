package com.benchwarmers.grads.grizzlystoreuser.controllers;

import com.benchwarmers.grads.grizzlystoreuser.entities.Account;
import com.benchwarmers.grads.grizzlystoreuser.entities.Profile;
import com.benchwarmers.grads.grizzlystoreuser.repositories.Account_Repository;
import com.benchwarmers.grads.grizzlystoreuser.repositories.Profile_Repository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


@RestController
@RequestMapping(path = "/")
public class UserProfileController {

    @Autowired
    private Account_Repository account_repository;

    @Autowired
    private Profile_Repository profile_repository;

    private Session session;
    private SessionFactory sessionFactory;

    @PostMapping(path = "/profiles") // Map ONLY POST Requests
    public @ResponseBody
    ResponseEntity getUserProfile(@RequestParam String accountID) {
        System.out.println(accountID);
        System.out.println("Account: " + accountID);
        Profile profile = profile_repository.findByUserAccount(UUID.fromString(accountID));
        if (profile == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Account ID does not exist");
        }
        System.out.println(profile);
        return ResponseEntity.ok(profile);
    }

    @PostMapping(path = "/account") // Map ONLY GET Requests
    public @ResponseBody
    void getUserAccount() {
        Account account = account_repository.findAccountByAccountEmailAddress("avi@gmail.com");
        System.out.println(account);
    }
}
