package com.benchwarmers.grads.grizzlystoreuser.controllers;

import com.benchwarmers.grads.grizzlystoreuser.entities.*;
import com.benchwarmers.grads.grizzlystoreuser.repositories.Account_Repository;
import com.benchwarmers.grads.grizzlystoreuser.repositories.Address_Repository;
import com.benchwarmers.grads.grizzlystoreuser.repositories.Profile_Repository;
import com.benchwarmers.grads.grizzlystoreuser.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/test")
public class TestController {

    @Autowired
    private Address_Repository addressRepo;

    @Autowired
    private Account_Repository accountRepository;

    @Autowired
    private Profile_Repository profileRepository;

    @Autowired
    private RoleRepository roleRepository;

    @RequestMapping(path = "/seed")
    public List<Account> seedData()
    {
        Account tempA = new Account();
        tempA.setAccountEmailAddress("pb07@gmail.com");
        tempA.setAdminStatus(false);
        tempA.setAccountPassword("password");

        Profile tempP = new Profile();
        tempP.setProfileFirstName("Paarth");
        tempP.setProfileLastName("Bhasin");
        tempP.setProfileImage("/Users/723313/Documents/Project/GrizzlyStore-React/src/images/profile_images/profile-pic.png");
        tempP.setProfilePhoneNumber("0403566491");
        tempA.setProfile(tempP);

        Address tempAddress = new Address();

        tempAddress.setAddressUnitNo("1");
        tempAddress.setAddressStreetNo("3");
        tempAddress.setAddressStreet("Fake");
        tempAddress.setAddressStreetType("St");
        tempAddress.setAddressCity("Suburb");
        tempAddress.setAddressPostcode("3924");
        tempAddress.setAddressState("VIC");
        tempAddress.setAddressCountry("Australia");
        //tempP.setAddress(tempAddress);
        //tempAddress.setProfile(tempP);
//        accountRepository.save(tempA);
//        profileRepository.save(tempP);
        tempP.setAddress(tempAddress);
        tempA.setProfile(tempP);

        addressRepo.save(tempAddress); // Cascade Insert. Inserts everything with just one insert

        //Account account = accountRepository.findByIdAccount(tempA.getIdAccount());
        System.out.println(accountRepository.findAll().get(0).getProfile());
        // TESTING CASCADE DELETE. WORKS BOTH WAYS
        //profileRepository.delete(profileRepository.findAll().get(0));
        return accountRepository.findAll();
    }

    @RequestMapping(path = "/addroles")
    public String seedRoles() {

        Role r1 = new Role(RoleName.ROLE_USER);

        Role r2 = new Role(RoleName.ROLE_ADMIN);

        roleRepository.save(r1);
        roleRepository.save(r2);

        return "Success with role updates.";

    }




}