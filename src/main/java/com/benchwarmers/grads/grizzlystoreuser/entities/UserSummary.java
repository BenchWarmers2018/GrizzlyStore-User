package com.benchwarmers.grads.grizzlystoreuser.entities;

import java.util.UUID;

public class UserSummary {

    private UUID id;
    private String username;
    private Profile profile;
    //Add admin here.




    //Add admin into this class

    public UserSummary(UUID id, String username, Profile profile) {
        this.id = id;
        this.username = username;
        this.profile = profile;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }
}
