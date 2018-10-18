package com.benchwarmers.grads.grizzlystoreuser.controllers;

import com.benchwarmers.grads.grizzlystoreuser.JsonResponse;
import com.benchwarmers.grads.grizzlystoreuser.entities.Account;
import com.benchwarmers.grads.grizzlystoreuser.entities.UserSummary;
import com.benchwarmers.grads.grizzlystoreuser.payload.JwtAuthenticationResponse;
import com.benchwarmers.grads.grizzlystoreuser.repositories.Account_Repository;
import com.benchwarmers.grads.grizzlystoreuser.repositories.RoleRepository;
import com.benchwarmers.grads.grizzlystoreuser.security.CurrentUser;
import com.benchwarmers.grads.grizzlystoreuser.security.JwtTokenProvider;
import com.benchwarmers.grads.grizzlystoreuser.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping(path = "/login")
public class LoginController {
    @Autowired
    private Account_Repository accRepo;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtTokenProvider tokenProvider;

    @PostMapping(path = "/authenticate", consumes = "application/json", produces = "application/json")
    public ResponseEntity login (@RequestBody Account account) {
        String enteredEmailAddress = account.getAccountEmailAddress().toLowerCase();
        String enteredPassword = account.getAccountPassword();
        JsonResponse response = new JsonResponse();


        if (!isNullOrEmpty(enteredEmailAddress) && !isNullOrEmpty(enteredPassword))
        {
            Account existingAccount = getExistingAccount(enteredEmailAddress);

            if (existingAccount == null)
            {
                createErrorMessage(response, "Email address or password is incorrect!");
            }
            else
            {
                Authentication authentication = authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                enteredEmailAddress,
                                enteredPassword
                        )
                );
                SecurityContextHolder.getContext().setAuthentication(authentication);
                if(authentication.isAuthenticated()) {
                    String jwt = tokenProvider.generateToken(authentication);
                    return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
                }
                else
                {
                    createErrorMessage(response, "Email address or password is incorrect!");
                }
            }
        }
        else
        {
            createErrorMessage(response,"An email address and a password must be entered!");
        }
        return response.createResponse();
    }




    @GetMapping("/user")
    @PreAuthorize("hasRole('USER')")
        public UserSummary getCurrentUser(@CurrentUser UserPrincipal currentUser) {
//        System.out.println(currentUser);
//        UUID id = currentUser.getIdAccount();
//        System.out.println("Id is " + id);
//        String username = currentUser.getUsername();
//        System.out.println("Uername is " + username);
            System.out.println("login/user: " + currentUser.toString());
            UserSummary userSummary = new UserSummary(currentUser.getIdAccount(), currentUser.getAccountEmailAddress(), currentUser.getProfile(), currentUser.isAdmin());
            return userSummary;
        }



    // Checks whether input is null and if it is empty
    private Boolean isNullOrEmpty(String input) {
        return (input.isEmpty() || input.equals(null));
    }

    private Account getExistingAccount(String emailAddress) {
        return accRepo.findAccountByAccountEmailAddress(emailAddress);
    }

    private Boolean passwordMatches(Account existingAccount, String enteredPassword) {
        return existingAccount.getAccountPassword().equals(enteredPassword);
    }

    private void createErrorMessage(JsonResponse response, String string) {
        response.setStatus(HttpStatus.NOT_ACCEPTABLE);
        response.addErrorMessage(string);
    }
}

