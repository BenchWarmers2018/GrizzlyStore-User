package com.benchwarmers.grads.grizzlystoreuser.controllers;

import com.benchwarmers.grads.grizzlystoreuser.entities.Account;
import com.benchwarmers.grads.grizzlystoreuser.entities.Address;
import com.benchwarmers.grads.grizzlystoreuser.entities.Profile;
import com.benchwarmers.grads.grizzlystoreuser.repositories.Account_Repository;
import com.benchwarmers.grads.grizzlystoreuser.repositories.Address_Repository;
import com.benchwarmers.grads.grizzlystoreuser.repositories.Profile_Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping(path = "/")
public class TestController {

    @Autowired
    private Account_Repository accRepo;

    @Autowired
    private Profile_Repository profileRepo;

    @Autowired
    private Address_Repository addressRepo;

    @RequestMapping(path = "seed")
    public Profile seedData()
    {
        Account tempA = new Account();
        tempA.setAccountEmailAddress("avimbn@gmail.com");
        tempA.setAdminStatus(false);
        tempA.setAccountPassword("password");

        Profile tempP = new Profile();
        tempP.setProfileFirstName("avtar");
        tempP.setProfileLastName("singh");
        tempP.setProfileImage("/Users/723313/Documents/Project/GrizzlyStore-React/src/images/profile_images/profile-pic.png");
        tempP.setProfilePhoneNumber("0403566491");
        tempP.setUserAccount(tempA);

        Address tempAddress = new Address();

        tempAddress.setAddressUnitNo("1");
        tempAddress.setAddressStreetNo("3");
        tempAddress.setAddressStreet("Fake");
        tempAddress.setAddressStreetType("St");
        tempAddress.setAddressCity("Suburb");
        tempAddress.setAddressPostcode("3924");
        tempAddress.setAddressState("VIC");
        tempAddress.setAddressCountry("Australia");
        tempAddress.setProfile(tempP);
        addressRepo.save(tempAddress);

        return tempP;
    }
}