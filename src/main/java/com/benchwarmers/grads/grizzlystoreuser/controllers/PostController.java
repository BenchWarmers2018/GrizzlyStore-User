package com.benchwarmers.grads.grizzlystoreuser.controllers;

import com.benchwarmers.grads.grizzlystoreuser.entities.Account;
import com.benchwarmers.grads.grizzlystoreuser.repositories.Account_Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.regex.Pattern;

@RestController
@RequestMapping(path = "/one")
public class PostController
{
    @Autowired
    Account_Repository newAccount;
    @RequestMapping(path = "/newAccount", method = RequestMethod.POST)
    public ResponseEntity createProfile(@RequestBody Account user)
    {
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
        newUser.setAccount_Password(user.getAccountPassword());
        newUser.setAccount_IsAdmin(false);

        //Also need to check for .com at end of email
        //Function checks if email address already exists in database and that it passes the checks beforehand
        if(!newAccount.existsByAccountEmailAddress(user.getAccountEmailAddress()) && emailCheck == false)
        {
            newAccount.save(newUser);
            return ResponseEntity.ok(newUser);
        }
        else if(emailCheck == true)
        {
            //if it is invalid the email is passed back as invalid
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email not in correct format");
        }
        else
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email already exists");
        }
    }
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


