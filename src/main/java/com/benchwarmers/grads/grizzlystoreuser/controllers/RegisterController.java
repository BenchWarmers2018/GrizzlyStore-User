package com.benchwarmers.grads.grizzlystoreuser.controllers;

import com.benchwarmers.grads.grizzlystoreuser.JsonResponse;
import com.benchwarmers.grads.grizzlystoreuser.entities.*;
import com.benchwarmers.grads.grizzlystoreuser.repositories.Account_Repository;
import com.benchwarmers.grads.grizzlystoreuser.repositories.RoleRepository;
import com.benchwarmers.grads.grizzlystoreuser.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.regex.Pattern;

@RestController
@CrossOrigin
@RequestMapping(path = "/register")
public class RegisterController
{
    @Autowired
    Account_Repository accountRepository;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtTokenProvider tokenProvider;

    @RequestMapping(path = "/create", method = RequestMethod.POST)
    public ResponseEntity createProfile(@RequestBody Account user)
    {
//        JsonResponse response = new JsonResponse();

        //Variable used to check if email contains '@' and followed by '.'
        boolean emailCheck = false;
        Account newUser = new Account();
        //This statement checks that the email address contains the at sign if not email check is set to true.
        if(isValid(user.getAccountEmailAddress()))
        {
            newUser.setAccountEmailAddress(user.getAccountEmailAddress());
        }
        else
        {
            emailCheck = true;
        }
        //Password is set here as well as admin is set to false by default
        newUser.setAccountPassword(passwordEncoder.encode(user.getAccountPassword()));
        newUser.setAccountIsAdmin(user.isAccountIsAdmin());


        //Also need to check for .com at end of email
        //Function checks if email address already exists in database and that it passes the checks beforehand
        if(!accountRepository.existsByAccountEmailAddress(user.getAccountEmailAddress()) && emailCheck == false)
        {
            Role userRole = roleRepository.findByName(RoleName.ROLE_USER);
            userRole.setName(RoleName.ROLE_USER);
            newUser.setRoles(Collections.singleton(userRole));
            JsonResponse response = new JsonResponse();

            //Creating empty profile for the new user
            Profile tempProfile = new Profile();
            newUser.setProfile(tempProfile);

            //Saving the new user with an account and profile.
            accountRepository.save(newUser);
            response.setStatus(HttpStatus.OK);
            response.addEntity(newUser);
            return response.createResponse();
        }
        else if(emailCheck == true)
        {
            JsonResponse response = new JsonResponse();
            //if it is invalid the email is passed back as invalid
            response.setStatus(HttpStatus.BAD_REQUEST);
            response.addErrorMessage("Email not in correct format");
            return response.createResponse();
        }
        else
        {
            JsonResponse response = new JsonResponse();
            response.setStatus(HttpStatus.BAD_REQUEST);
            response.addErrorMessage("Email address already exists");
            return response.createResponse();
        }


    }


    //This function checks if an email address contains an @ and followed by a '.'
    public static boolean isValid(String email)
    {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pat.matcher(email).matches();
    }
}


