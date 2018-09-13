package com.benchwarmers.grads.grizzlystoreuser.controllers;

import com.benchwarmers.grads.grizzlystoreuser.JsonResponse;
import com.benchwarmers.grads.grizzlystoreuser.entities.Profile;
import com.benchwarmers.grads.grizzlystoreuser.repositories.Profile_Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


@RestController
@RequestMapping(path = "/user")
public class UserProfileController {

    @Autowired
    private Profile_Repository profile_repository;

    @PostMapping(path = "/profile") // Map ONLY POST Requests
    public @ResponseBody
    ResponseEntity getUserProfile(@RequestParam String accountID) {
        System.out.println(accountID);
        System.out.println("Account: " + accountID);
        JsonResponse response = new JsonResponse();
        Profile profile = profile_repository.findByUserAccount(UUID.fromString(accountID));
        if (profile == null) {
            createErrorMessage(response, "Account ID " + accountID + " doesn't exist.");
        } else {
            response.setStatus(HttpStatus.OK);
            response.addEntity(profile);
        }
        System.out.println(profile);
        return response.createResponse();
    }

    private void createErrorMessage(JsonResponse response, String string) {
        response.setStatus(HttpStatus.NOT_ACCEPTABLE);
        response.addErrorMessage(string);
    }
}
