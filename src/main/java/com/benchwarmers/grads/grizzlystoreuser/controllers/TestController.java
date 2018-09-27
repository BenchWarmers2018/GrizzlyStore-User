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
        tempA.setAccountEmailAddress("avi@gmail.com");
        tempA.setAdminStatus(false);
        tempA.setAccountPassword("password");
        accRepo.save(tempA);

        Profile tempP = new Profile();
        tempP.setProfileFirstName("avtar");
        tempP.setProfileLastName("singh");
        tempP.setProfileImage("image");
        tempP.setProfilePhoneNumber("0403566491");
        tempP.setUserAccount(tempA);
        profileRepo.save(tempP);

        Address tempAddress = new Address();
        tempAddress.setAddressCity("Melbourne, Victoria");
        tempAddress.setAddressCountry("Australia");
        tempAddress.setAddressLine1("95 Bear Avenue, Grizzly Park");
        tempAddress.setProfile(tempP);
        addressRepo.save(tempAddress);

        return tempP;
    }
}