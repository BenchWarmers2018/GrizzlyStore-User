package com.benchwarmers.grads.grizzlystoreuser.repositories;

import com.benchwarmers.grads.grizzlystoreuser.entities.Address;
import com.benchwarmers.grads.grizzlystoreuser.entities.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Address_Repository extends JpaRepository<Address, Integer> {
    Address findAddressByProfile(Profile profile);
}