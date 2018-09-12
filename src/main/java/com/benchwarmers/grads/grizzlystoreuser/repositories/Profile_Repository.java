package com.benchwarmers.grads.grizzlystoreuser.repositories;

import com.benchwarmers.grads.grizzlystoreuser.entities.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface Profile_Repository extends JpaRepository<Profile, Integer> {
    Profile findByUserAccount(UUID accountID);
}
