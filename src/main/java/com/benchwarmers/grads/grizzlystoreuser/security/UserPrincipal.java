package com.benchwarmers.grads.grizzlystoreuser.security;

import com.benchwarmers.grads.grizzlystoreuser.entities.Account;
import com.benchwarmers.grads.grizzlystoreuser.entities.Profile;
import com.benchwarmers.grads.grizzlystoreuser.repositories.Profile_Repository;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

public class UserPrincipal implements UserDetails {

    private UUID id;

    private Profile profile;

    private String username;

    @Autowired
    Profile_Repository profile_repository;

    @JsonIgnore
    private String email;

    @JsonIgnore
    private String password;

    private Collection<? extends GrantedAuthority> authorities;

    public UserPrincipal(UUID id, String username, String email, String password,Profile profile , Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.username = username;
        this.email = email;
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

    public UUID getId() {
        return id;
    }


    public Profile getProfile() {
        return profile;
    }

    public String getEmail() {
        return email;
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
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id);
    }

}
