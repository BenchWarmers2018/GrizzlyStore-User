package com.benchwarmers.grads.grizzlystoreuser.entities;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Profile")
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_Profile", nullable = false)
    private Integer id_Profile;

    @Column(name = "profile_firstname", nullable = false)
    private String profile_FirstName;

    @Column(name = "profile_lastname", nullable = false)
    private String profile_LastName;

    @Column(name = "profile_phonenumber", nullable = false)
    private String profile_PhoneNumber;

    @Column(name = "profile_Image", nullable = false)
    private String profile_Image;

    @CreationTimestamp
    @Column(name = "last_modified", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date last_modified;


    @OneToOne(cascade =  CascadeType.ALL,
            orphanRemoval = true
    )
    @JoinColumn(name = "id_account_foreign", nullable = false)
    private Account userAccount;


    //Getter and Setters.
    public Account getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(Account userAccount) {
        this.userAccount = userAccount;
    }

    public Integer getId_Profile() {
        return id_Profile;
    }

    public void setId_Profile(Integer id_Profile) {
        this.id_Profile = id_Profile;
    }

    public String getProfile_FirstName() {
        return profile_FirstName;
    }

    public void setProfile_FirstName(String profile_FirstName) {
        this.profile_FirstName = profile_FirstName;
    }

    public String getProfile_LastName() {
        return profile_LastName;
    }

    public void setProfile_LastName(String profile_LastName) {
        this.profile_LastName = profile_LastName;
    }

    public String getProfile_PhoneNumber() {
        return profile_PhoneNumber;
    }

    public void setProfile_PhoneNumber(String profile_PhoneNumber) {
        this.profile_PhoneNumber = profile_PhoneNumber;
    }

    public String getProfile_Image() {
        return profile_Image;
    }

    public void setProfile_Image(String profile_Image) {
        this.profile_Image = profile_Image;
    }

    public Date getLast_modified() {
        return last_modified;
    }

    public void setLast_modified(Date last_modified) {
        this.last_modified = last_modified;
    }


}
