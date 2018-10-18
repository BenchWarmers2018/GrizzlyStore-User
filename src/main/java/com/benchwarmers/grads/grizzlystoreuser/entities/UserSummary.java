package com.benchwarmers.grads.grizzlystoreuser.entities;

import java.util.UUID;

public class UserSummary {

    private UUID idAccount;
    private String accountEmailAddress;
    private Profile profile;
    private boolean isAdmin;

    public UserSummary(UUID idAccount, String accountEmailAddress, Profile profile, boolean isAdmin) {
        this.idAccount = idAccount;
        this.accountEmailAddress = accountEmailAddress;
        this.profile = profile;
        this.isAdmin = isAdmin;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public UUID getIdAccount() {
        return idAccount;
    }

    public void setIdAccount(UUID idAccount) {
        this.idAccount = idAccount;
    }

    public String getAccountEmailAddress() {
        return accountEmailAddress;
    }

    public void setUsername(String accountEmailAddress) {
        this.accountEmailAddress = accountEmailAddress;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }
}
