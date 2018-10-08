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

    @Autowired
    private Profile_Repository profile_repository;

    @CrossOrigin
    @RequestMapping(value = "/profile", method = POST, consumes = MediaType.ALL_VALUE)
    public @ResponseBody
    ResponseEntity getUserProfileFromID(@RequestParam String accountID) {
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
    public @ResponseBody
    String postUpdatedProfileDetails(@RequestBody String json, @RequestHeader(value="accountID") String accountID,
                                     @RequestHeader(value="SUBMISSION_TYPE") String type) {
        System.out.println(json + '\n' + accountID + '\n' + type);
        JSONObject profileUpdated = new JSONObject(json);
        System.out.println("Updated Profiles: " + profileUpdated);
        Account account = account_repository.findByIdAccount(UUID.fromString(accountID));
        Profile profile = account.getProfile();
        String output = "";
        switch (type) {
            case "Personal":
                output = updatePersonalDetails(profileUpdated, profile);
                break;
            case "Password":
                output = updatePassword(profileUpdated, account);
                break;
            case "Address":
                output = updateAddress(profileUpdated, profile);
                break;
            default:
                break;
        }
        System.out.println(account);
        return output;
    }

    private String updatePersonalDetails(JSONObject details, Profile profile) {
        try {
            String newPhone = details.getString("phone");
            String firstName = details.getString("firstName");
            String lastName = details.getString("lastName");
            if (!newPhone.isEmpty()) {
                profile.setProfilePhoneNumber(newPhone);
            }
            if (!firstName.isEmpty()) {
                profile.setProfileFirstName(firstName);
            }
            if (!lastName.isEmpty()) {
                profile.setProfileLastName(lastName);
            }
            profile_repository.save(profile);
            return "Personal details updated successfully!";

        }
        catch (Exception e) {
            return "Unable to update personal details. " + e.toString();
        }

    }

    private String updatePassword(JSONObject password, Account account) {
        String newPassword = password.getString("password");
        try {
            account.setAccountPassword(newPassword);
            account_repository.save(account);
            return "Password updated successfully!";
        }
        catch (Exception e) {
            return "Unable to update password. " + e.toString();
        }
    }

    private String updateAddress(JSONObject addressDetails, Profile profile) {

        try {
            Address address = address_repository.findAddressByProfile(profile);
            String country = addressDetails.getString("country");
            String city = addressDetails.getString("city");
            String postcode = addressDetails.getString("postcode");
            String streetType = addressDetails.getString("streetType");
            String street = addressDetails.getString("street");
            String streetNo = addressDetails.getString("streetNo");
            String state = addressDetails.getString("state");
            String unitNo = addressDetails.getString("unitNo");
            if (!country.isEmpty())
                address.setAddressCountry(country);
            if (!postcode.isEmpty())
                address.setAddressPostcode(postcode);
            if (!state.isEmpty())
                address.setAddressState(state);
            if (!city.isEmpty())
                address.setAddressCity(city);
            if (!street.isEmpty())
                address.setAddressStreet(street);
            if (!streetNo.isEmpty())
                address.setAddressStreet(streetNo);
            if (!streetType.isEmpty())
                address.setAddressStreet(streetType);
            if (!unitNo.isEmpty())
                address.setAddressStreet(unitNo);
            address_repository.save(address);
            return "Address updated successfully!";
        }
        catch (Exception e) {
            return "Unable to update address. " + e.toString();
        }
    }


    private void createErrorMessage(JsonResponse response, String string) {
        response.setStatus(HttpStatus.NOT_ACCEPTABLE);
        response.addErrorMessage(string);
    }
}
