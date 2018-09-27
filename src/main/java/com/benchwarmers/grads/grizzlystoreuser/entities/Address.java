package com.benchwarmers.grads.grizzlystoreuser.entities;

import com.benchwarmers.grads.grizzlystoreuser.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Address")
public class Address extends Data {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_Address", updatable = false, nullable = false)
    private Integer idAddress;

    @Column(name = "address_Line1", nullable = false)
    @Length(max=256, message="Address Line 1 must be less than or equal to {max} characters")
    private String addressLine1;

    @Column(name = "address_Line2")
    @Length(max=256, message="Address Line 2 must be less than or equal to {max} characters")
    private String addressLine2;

    @Column(name = "address_Line3")
    @Length(max=256, message="Address Line 3 must be less than or equal to {max} characters")
    private String addressLine3;

    @Column(name = "address_City", nullable = false)
    @Length(max=50, message="The city name must be less than or equal to {max} characters")
    private String addressCity;

    @Column(name = "address_State")
    @Length(max=3, message="The state must be less than or equal to {max} characters")
    private String addressState;

    @Column(name = "address_Country", nullable = false)
    @Length(max=50, message="The country name must be less than or equal to {max} characters")
    private String addressCountry;

    @Column(name = "address_Postcode")
    @Length(min=4, max=4, message="The postcode must be equal to {max} characters")
    private String addressPostcode;

    @CreationTimestamp
    @Column(name = "last_modified", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModified;

    @OneToOne(cascade =  CascadeType.ALL,
            orphanRemoval = true
    )
    @JoinColumn(name = "id_profile_foreign", nullable = false)
    private Profile profile;

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public String getAddressLine3() {
        return addressLine3;
    }

    public void setAddressLine3(String addressLine3) {
        this.addressLine3 = addressLine3;
    }

    public String getAddressCity() {
        return addressCity;
    }

    public void setAddressCity(String addressCity) {
        this.addressCity = addressCity;
    }

    public String getAddressState() {
        return addressState;
    }

    public void setAddressState(String addressState) { this.addressState = addressState.toUpperCase(); }

    public String getAddressCountry() {
        return addressCountry;
    }

    public void setAddressCountry(String addressCountry) {
        this.addressCountry = addressCountry;
    }

    public String getAddressPostcode() {
        return addressPostcode;
    }

    public void setAddressPostcode(String addressPostcode) {
        this.addressPostcode = addressPostcode;
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
        this.profile = profile;
    }

    public void setIdAddress(Integer idAddress) {
        this.idAddress = idAddress;
    }

    public Integer getIdAddress() {
        return idAddress;
    }
}
