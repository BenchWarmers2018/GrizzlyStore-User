//package com.benchwarmers.grads.grizzlystoreuser.controllers;
//
//import com.benchwarmers.grads.grizzlystoreuser.entities.Account;
//import com.benchwarmers.grads.grizzlystoreuser.repositories.Account_Repository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.Collections;
//import java.util.List;
//
//@RestController
//@RequestMapping(path = "/")
//public class LoginController {
//    @Autowired
//    private Account_Repository accRepo;
//
//    //    @PostMapping(path = "/members", consumes = "application/json", produces = "application/json")
////    @PostMapping(path = "login", consumes = "application/json", produces = "application/json")
//    @RequestMapping(path = "login")
//    public void login (@RequestBody Account account) {
//        if (accountExists(account)) {
//
//        }
//        else {
////            Return error message here
//        }
//    }
//
//    public Boolean getExistingAccount(String emailAddress) {
//        return accRepo.findAccountByAccountEmailAddress(account.getAccountEmailAddress()) != null;
//    }
//
////    public Boolean passwordMatches(Account account) {
////        return
////    }
//}