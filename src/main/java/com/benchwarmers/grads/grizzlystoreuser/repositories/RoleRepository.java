package com.benchwarmers.grads.grizzlystoreuser.repositories;

import com.benchwarmers.grads.grizzlystoreuser.entities.Role;
import com.benchwarmers.grads.grizzlystoreuser.entities.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(RoleName roleName);
}
