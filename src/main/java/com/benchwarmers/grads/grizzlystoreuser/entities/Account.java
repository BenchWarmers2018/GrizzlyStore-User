package com.benchwarmers.grads.grizzlystoreuser.entities;

import com.benchwarmers.grads.grizzlystoreuser.Data;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.Length;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.*;

@Entity
@Table(name = "Account")
public class Account extends Data {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Type(type="uuid-char")
    @Column(name = "id_Account", updatable = false, nullable = false)
    private UUID idAccount;

    @Column(name = "account_EmailAddress", nullable = false, unique = true)
    @Email
    @Length(max=256, message="Emails must be less than or equal to {max} characters")
    private String accountEmailAddress;

    @Column(name = "account_Password", nullable = false)
    @Length(max=256, message="Passwords must be less than or equal to {max} characters")
    private String accountPassword;

    @Column(name = "account_IsAdmin", nullable = false)
    //@Type(type = "TrueFalseType")
    private boolean accountIsAdmin;

    @CreationTimestamp
    @Column(name = "DateTime", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModified;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    @OneToOne(mappedBy = "userAccount", orphanRemoval = true, cascade = CascadeType.ALL)
    private Profile profile;

    //Getter and Setters

    public UUID getIdAccount() {
        return idAccount;
    }

    public void setIdAccount(UUID id_account) {
        this.idAccount = id_account;
    }

    public String getAccountEmailAddress() {
        return accountEmailAddress;
    }

    public void setAccountEmailAddress(String account_EmailAddress) {
        this.accountEmailAddress = account_EmailAddress.toLowerCase();
    }

    public String getAccountPassword() {
        return accountPassword;
    }

    public void setAccountPassword(String account_Password) {
        this.accountPassword = account_Password;
    }

    public boolean isAdmin() {
        return accountIsAdmin;
    }

    public void setAdminStatus(boolean status) {
        this.accountIsAdmin = status;
    }

    public Date getLastModified() {
        return lastModified;
    }

    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        if (profile != null)
            profile.setUserAccount(this);
        this.profile = profile;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
