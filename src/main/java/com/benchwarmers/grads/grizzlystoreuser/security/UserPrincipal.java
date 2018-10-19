package com.benchwarmers.grads.grizzlystoreuser.security;

import com.benchwarmers.grads.grizzlystoreuser.entities.Account;
import com.benchwarmers.grads.grizzlystoreuser.entities.Profile;
import com.benchwarmers.grads.grizzlystoreuser.repositories.Profile_Repository;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

public class UserPrincipal implements UserDetails {

    private UUID idAccount;

    private Profile profile;

    private String username;

    @Autowired
    Profile_Repository profile_repository;

    @JsonIgnore
    private String accountEmailAddress;

    //Add admin into this class

    //Add admin into this class

    @JsonIgnore
    private String password;

    private Collection<? extends GrantedAuthority> authorities;

    public UserPrincipal(UUID idAccount, String username, String accountEmailAddress, String password, Profile profile , Collection<? extends GrantedAuthority> authorities) {
        this.idAccount = idAccount;
        this.username = username;
        this.accountEmailAddress = accountEmailAddress;
        this.password = password;
        this.profile = profile;
        this.authorities = authorities;
    }


    public static UserPrincipal create(Account account) {

        List<GrantedAuthority> authorities = account.getRoles().stream().map(role ->
                new SimpleGrantedAuthority(role.getName().name())
        ).collect(Collectors.toList());
        Profile profile = account.getProfile();

        return new UserPrincipal(
                account.getIdAccount(),
                account.getAccountEmailAddress(),
                account.getAccountEmailAddress(),
                account.getAccountPassword(),
                profile,
                authorities
        );
    }

    public UUID getIdAccount() {
        return idAccount;
    }


    public Profile getProfile() {
        return profile;
    }

    public String getAccountEmailAddress() {
        return accountEmailAddress;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserPrincipal that = (UserPrincipal) o;
        return Objects.equals(idAccount, that.idAccount);
    }

    @Override
    public int hashCode() {

        return Objects.hash(idAccount);
    }

}
