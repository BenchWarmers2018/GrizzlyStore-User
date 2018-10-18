package com.benchwarmers.grads.grizzlystoreuser.entities;

import java.util.UUID;

public class UserSummary {

    private UUID idAccount;
    private String accountEmailAddress;
    private Profile profile;
    private boolean accountIsAdmin;

    public UserSummary(UUID idAccount, String accountEmailAddress, Profile profile, boolean accountIsAdmin) {
        this.idAccount = idAccount;
        this.accountEmailAddress = accountEmailAddress;
        this.profile = profile;
        this.accountIsAdmin = accountIsAdmin;
    }

    public boolean isAdmin() {
        return accountIsAdmin;
    }

    public void setAdmin(boolean accountIsAdmin) {
        this.accountIsAdmin = accountIsAdmin;
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

    public void setAccountEmailAddress(String accountEmailAddress) {
        this.accountEmailAddress = accountEmailAddress;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }
}
