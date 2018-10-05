package com.benchwarmers.grads.grizzlystoreuser.controllers;

import com.benchwarmers.grads.grizzlystoreuser.JsonResponse;
import com.benchwarmers.grads.grizzlystoreuser.entities.Account;
import com.benchwarmers.grads.grizzlystoreuser.entities.Address;
import com.benchwarmers.grads.grizzlystoreuser.entities.Profile;
import com.benchwarmers.grads.grizzlystoreuser.repositories.Account_Repository;
import com.benchwarmers.grads.grizzlystoreuser.repositories.Address_Repository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static org.springframework.web.bind.annotation.RequestMethod.POST;


@RestController
@RequestMapping(path = "/user") // User profile
public class UserProfileController {

    @Autowired
    private Account_Repository account_repository;

    @Autowired
    private Address_Repository address_repository;

    @CrossOrigin
    @RequestMapping(value = "/profile", method = POST, consumes = MediaType.ALL_VALUE)
    public @ResponseBody ResponseEntity getUserProfileFromID(@RequestParam String accountID) {
        JsonResponse response = new JsonResponse();
        Account account = account_repository.findByIdAccount(UUID.fromString(accountID));
        if (account == null) {
            createErrorMessage(response, "Account ID " + accountID + " doesn't exist.");
            return response.createResponse();
        }
        Profile profile = account.getProfile();
        if (profile == null) {
            createErrorMessage(response, "Account has no profile.");
        } else {
            Address address = address_repository.findAddressByProfile(profile);
            response.setStatus(HttpStatus.OK);
            response.addEntity(profile);
            response.addEntity(address);
            System.out.println("Getting here");
        }
        System.out.println(profile);
        return response.createResponse();
    }

    @CrossOrigin
    @RequestMapping(value = "/profile-account", method = POST, consumes = MediaType.ALL_VALUE)
    public @ResponseBody
    ResponseEntity getUserProfileFromAccount(@RequestBody Account account) {
        JsonResponse response = new JsonResponse();
        Account newAcc = account_repository.findByIdAccount(account.getIdAccount());
        if (newAcc == null) {
            createErrorMessage(response, "Account ID " + account.getIdAccount().toString() + " doesn't exist.");
            return response.createResponse();
        }
        Profile profile = newAcc.getProfile();
        if (profile == null) {
            createErrorMessage(response, "Account has no profile.");
        } else {
            Address address = address_repository.findAddressByProfile(profile);
            response.setStatus(HttpStatus.OK);
            response.addEntity(profile);
            response.addEntity(address);
        }
        System.out.println(profile);
        return response.createResponse();
    }

    @CrossOrigin
    @RequestMapping(value = "/update-profile", method = POST, consumes = MediaType.ALL_VALUE)
    public @ResponseBody String postUpdatedProfileDetails(@RequestBody String json) {
        JSONObject profileUpdated = new JSONObject(json);
        System.out.println("Updated Profiles: " + profileUpdated);
        String uuid = profileUpdated.getString("accountID");
        Account account = account_repository.findByIdAccount(UUID.fromString(uuid));
        if (profileUpdated.get("phone").toString().isEmpty())
        updateDetails(profileUpdated);
        Profile profile = account.getProfile();

        System.out.println(account);
        return "Profile Updating Successful";
    }

    private void updateDetails(JSONObject details) {

    }

    private void createErrorMessage(JsonResponse response, String string) {
        response.setStatus(HttpStatus.NOT_ACCEPTABLE);
        response.addErrorMessage(string);
    }
}
