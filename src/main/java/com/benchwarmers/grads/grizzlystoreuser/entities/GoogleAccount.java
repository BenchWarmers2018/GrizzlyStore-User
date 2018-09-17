//package com.benchwarmers.grads.grizzlystoreuser.entities;
//
//import com.benchwarmers.grads.grizzlystoreuser.Data;
//import org.hibernate.annotations.CreationTimestamp;
//import org.hibernate.annotations.GenericGenerator;
//
//import javax.persistence.*;
//import javax.validation.constraints.Email;
//import java.util.Date;
//import java.util.UUID;
//
//@Entity
//@Table(name="GoogleAccount")
//public class GoogleAccount extends Data {
//    @Id
//    @GeneratedValue(generator = "UUID")
//    @GenericGenerator(
//            name = "UUID",
//            strategy = "org.hibernate.id.UUIDGenerator"
//    )
//    @Column(name = "id_Account", updatable = false, nullable = false)
//    private UUID idGoogleAccount;
//
//    @Column(name = "account_EmailAddress", nullable = false, unique = true)
//    @Email
//    private String googleAccountEmailAddress;
//
//    @CreationTimestamp
//    @Column(name = "DateTime", nullable = false)
//    @Temporal(TemporalType.TIMESTAMP)
//    private Date lastModified;
//
//    @OneToOne(mappedBy = "googleAccount")
//    private Profile profile;
//
//    public UUID getIdGoogleAccount() {
//        return idGoogleAccount;
//    }
//
//    public void setIdGoogleAccount(UUID idGoogleAccount) {
//        this.idGoogleAccount = idGoogleAccount;
//    }
//
//    public String getGoogleAccountEmailAddress() {
//        return googleAccountEmailAddress;
//    }
//
//    public void setGoogleAccountEmailAddress(String googleAccountEmailAddress) {
//        this.googleAccountEmailAddress = googleAccountEmailAddress.toLowerCase();
//    }
//
//    public Date getLastModified() {
//        return lastModified;
//    }
//
//    public void setLastModified(Date lastModified) {
//        this.lastModified = lastModified;
//    }
//}
