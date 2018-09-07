package com.benchwarmers.grads.grizzlystoreuser.entities;

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
public class Account {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id_Account", updatable = false, nullable = false)
    private UUID id_account;

    @Column(name = "account_EmailAddress", nullable = false, unique = true)
    @Email
    private String account_EmailAddress;

    @Column(name = "account_Password", nullable = false)
    private String account_Password;

    @Column(name = "account_IsAdmin", nullable = false)
    private boolean account_IsAdmin;

    @CreationTimestamp
    @Column(name = "DateTime", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date DateTime;




    //Getter and Setters

    public UUID getId_account() {
        return id_account;
    }

    public void setId_account(UUID id_account) {
        this.id_account = id_account;
    }

    public String getAccount_EmailAddress() {
        return account_EmailAddress;
    }

    public void setAccount_EmailAddress(String account_EmailAddress) {
        this.account_EmailAddress = account_EmailAddress;
    }

    public String getAccount_Password() {
        return account_Password;
    }

    public void setAccount_Password(String account_Password) {
        this.account_Password = account_Password;
    }

    public boolean isAccount_IsAdmin() {
        return account_IsAdmin;
    }

    public void setAccount_IsAdmin(boolean account_IsAdmin) {
        this.account_IsAdmin = account_IsAdmin;
    }

    public Date getDateTime() {
        return DateTime;
    }

    public void setDateTime(Date dateTime) {
        DateTime = dateTime;
    }


}
