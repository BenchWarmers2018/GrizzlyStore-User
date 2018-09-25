package com.benchwarmers.grads.grizzlystoreuser.controllers;

import com.benchwarmers.grads.grizzlystoreuser.JsonResponse;
import com.benchwarmers.grads.grizzlystoreuser.entities.Account;
import com.benchwarmers.grads.grizzlystoreuser.repositories.Account_Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.regex.Pattern;

@RestController
@RequestMapping(path = "/register")
public class RegisterController
{
    @Autowired
    Account_Repository newAccount;
    @CrossOrigin
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
        newUser.setAccountPassword(user.getAccountPassword());
        newUser.setAdminStatus(false);

        //Also need to check for .com at end of email
        //Function checks if email address already exists in database and that it passes the checks beforehand
        if(!newAccount.existsByAccountEmailAddress(user.getAccountEmailAddress()) && emailCheck == false)
        {
            JsonResponse response = new JsonResponse();
            newAccount.save(newUser);
            response.setStatus(HttpStatus.OK);
            response.addEntity(newUser);
            //return ResponseEntity.ok(newUser);
            return response.createResponse();
        }
        else if(emailCheck == true)
        {
            JsonResponse response = new JsonResponse();
            //if it is invalid the email is passed back as invalid
            response.setStatus(HttpStatus.BAD_REQUEST);
            response.addErrorMessage("Email not in correct format");
            return response.createResponse();
            //return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email not in correct format");
        }
        else
        {
            JsonResponse response = new JsonResponse();
            response.setStatus(HttpStatus.BAD_REQUEST);
            response.addErrorMessage("Email address already exists");
            return response.createResponse();
            //return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email already exists");
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


