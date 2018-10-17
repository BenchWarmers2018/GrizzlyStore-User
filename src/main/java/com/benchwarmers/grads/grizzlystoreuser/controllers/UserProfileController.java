package com.benchwarmers.grads.grizzlystoreuser.controllers;

import com.benchwarmers.grads.grizzlystoreuser.JsonResponse;
import com.benchwarmers.grads.grizzlystoreuser.entities.Account;
import com.benchwarmers.grads.grizzlystoreuser.entities.Address;
import com.benchwarmers.grads.grizzlystoreuser.entities.Profile;
import com.benchwarmers.grads.grizzlystoreuser.repositories.Account_Repository;
import com.benchwarmers.grads.grizzlystoreuser.repositories.Address_Repository;
import com.benchwarmers.grads.grizzlystoreuser.repositories.Profile_Repository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.UUID;

import static org.springframework.web.bind.annotation.RequestMethod.POST;


@RestController
@RequestMapping(path = "/user") // User profile
@CrossOrigin
public class UserProfileController {

    @Autowired
    private Account_Repository account_repository;

    @Autowired
    private Address_Repository address_repository;

    @Autowired
    private Profile_Repository profile_repository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @RequestMapping(value = "/profile", method = POST)
    public @ResponseBody
    ResponseEntity getUserProfileFromID(@RequestBody Account acc) {
        JsonResponse response = new JsonResponse();
        Account account = account_repository.findByIdAccount(acc.getIdAccount());
        if (account == null) {
            createErrorMessage(response, "This Account doesn't exist.");
            return response.createResponse();
        }
        Profile profile = account.getProfile();
        if (profile == null) {
            createErrorMessage(response, "Account has no profile.");
            return response.createResponse();
        }
        response.setStatus(HttpStatus.OK);
        response.addEntity(account);
        return response.createResponse();
    }


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
            return response.createResponse();
        }
        response.setStatus(HttpStatus.OK);
        response.addEntity(account);
        System.out.println("Getting here");
        System.out.println(profile.toString());
        return response.createResponse();
    }


    @RequestMapping(value = "/update-password", method = POST, consumes = MediaType.ALL_VALUE)
    public @ResponseBody
    ResponseEntity postUpdatedProfileDetails(@RequestBody String json) {
        JsonResponse response = new JsonResponse();
        JSONObject profileUpdated = new JSONObject(json);
        String accountID = profileUpdated.getString("accountID");
        Account account = account_repository.findByIdAccount(UUID.fromString(accountID));
        //Update password in another method.
        updatePassword(response, profileUpdated, account);

        //Refreshed Account.
        account = account_repository.findByIdAccount(UUID.fromString(accountID));
        System.out.println(account);
        response.addEntity(account);
        return response.createResponse();
    }

    @RequestMapping(value = "/update-personal", method = POST, consumes = "multipart/form-data")
    public @ResponseBody
    ResponseEntity uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("firstName") String firstName,
                              @RequestParam("lastName") String lastName, @RequestParam("phone") String phone,
                              @RequestParam("accountID") String accountID) {

        JsonResponse response = new JsonResponse();
        Account account = account_repository.findByIdAccount(UUID.fromString(accountID));
        if (account == null) {
            createErrorMessage(response, "Account ID " + accountID + " doesn't exist.");
            return response.createResponse();
        }
        Profile profile = account.getProfile();
        if (profile == null) {
            createErrorMessage(response, "Account has no profile.");
            return response.createResponse();
        }
        if (!file.isEmpty()) {
            try {
                System.out.println("POST REQUEST ACCEPTED");
                String uploadDir = "/opt/images/grizzlystore/profile/";
                String filename = file.getOriginalFilename();
                String filePath = uploadDir + filename;
                if (!new File(uploadDir).exists()) {
                    System.out.println("Directory does not exist");
                    new File(uploadDir).mkdirs();
                }
                File dest = new File(filePath);
                file.transferTo(dest);
                profile.setProfileImage("http://bw.ausgrads.academy/images/grizzlystore/profile/" + filename);
            } catch (Exception e) {
                System.out.println(e.toString());
                createErrorMessage(response, "Unable to update user details. " + e.toString());
                return response.createResponse();
            }
        }
        profile.setProfileFirstName(firstName);
        profile.setProfileLastName(lastName);
        profile.setProfilePhoneNumber(phone);
        profile_repository.save(profile);
        response.setStatus(HttpStatus.OK);
        response.addEntity(account);
        return response.createResponse();
    }

    private void updatePassword(JsonResponse response, JSONObject password, Account account) {
        String currentPassword = password.getString("currentPassword");
        String newPassword = password.getString("password");
        String encodedPassword = passwordEncoder.encode(currentPassword);
        System.out.println("Current :" + account.getAccountPassword() + " New" + encodedPassword);

        if(passwordEncoder.matches(currentPassword, account.getAccountPassword()))
        {
            try {
                account.setAccountPassword(passwordEncoder.encode(newPassword));
                account_repository.save(account);
                response.setStatus(HttpStatus.OK);

            } catch (Exception e) {
                createErrorMessage(response, "Unable to update password. " + e.toString());
            }
        }
        else {
            createErrorMessage(response, "Your current Password didn't match.");
        }

    }


    private void createErrorMessage(JsonResponse response, String string) {
        response.setStatus(HttpStatus.NOT_ACCEPTABLE);
        response.addErrorMessage(string);
    }


    @RequestMapping(path = "/update-address", method = RequestMethod.POST)
    public ResponseEntity updateProfileAddress(@RequestBody Account account)
    {
        JsonResponse jsonResponse = new JsonResponse();
        if(account_repository.existsByIdAccount(account.getIdAccount()))
        {
            Account userAccount = account_repository.findByIdAccount(account.getIdAccount());
            Address address = userAccount.getProfile().getAddress();
            Address newAddress = account.getProfile().getAddress();
            if(address != null && newAddress!= null)
            {
                address.setAddressUnitNo(newAddress.getAddressUnitNo());
                address.setAddressStreetNo(newAddress.getAddressStreetNo());
                address.setAddressStreet(newAddress.getAddressStreet());
                address.setAddressStreetType(newAddress.getAddressStreetType());
                address.setAddressCity(newAddress.getAddressCity());
                address.setAddressState(newAddress.getAddressState());
                address.setAddressCountry(newAddress.getAddressCountry());
                address.setAddressPostcode(newAddress.getAddressPostcode());
            }
            else {
                userAccount.getProfile().setAddress(newAddress);
            }
            account_repository.save(userAccount);
            jsonResponse.setStatus(HttpStatus.OK);
            jsonResponse.addEntity(userAccount);
            return jsonResponse.createResponse();
        }
        else
        {
            jsonResponse.setStatus(HttpStatus.BAD_REQUEST);
            jsonResponse.addErrorMessage("No accounts as such");
            return jsonResponse.createResponse();
        }

    }




}
