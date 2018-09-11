package com.benchwarmers.grads.grizzlystoreuser.controllers;

import com.benchwarmers.grads.grizzlystoreuser.entities.Account;
import com.benchwarmers.grads.grizzlystoreuser.entities.Profile;
import com.benchwarmers.grads.grizzlystoreuser.repositories.Account_Repository;
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

    @RequestMapping(path = "seed")
    public Profile seedData()
    {
        Account tempA = new Account();
        tempA.setAccountEmailAddress("avi@gmail.com");
        tempA.setAccount_IsAdmin(false);
        tempA.setAccount_Password("password");
        accRepo.save(tempA);

        Profile tempP = new Profile();
        tempP.setProfile_FirstName("avtar");
        tempP.setProfile_LastName("singh");
        tempP.setProfile_Image("image");
        tempP.setProfile_PhoneNumber("0403566491");
        tempP.setUserAccount(tempA);
        profileRepo.save(tempP);

        return tempP;
    }
}
