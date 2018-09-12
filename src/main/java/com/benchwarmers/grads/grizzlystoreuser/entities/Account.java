package com.benchwarmers.grads.grizzlystoreuser.entities;

import com.benchwarmers.grads.grizzlystoreuser.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name="Account")
public class Account extends Data {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id_Account", updatable = false, nullable = false)
    private UUID idAccount;

    @Column(name = "account_EmailAddress", nullable = false, unique = true)
    @Email
    private String accountEmailAddress;

    @Column(name = "account_Password", nullable = false)
    private String accountPassword;

    @Column(name = "account_IsAdmin", nullable = false)
    private boolean accountIsAdmin = false;

    @CreationTimestamp
    @Column(name = "DateTime", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModified;

    @OneToOne(mappedBy = "userAccount")
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
        this.accountEmailAddress = account_EmailAddress;
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
}