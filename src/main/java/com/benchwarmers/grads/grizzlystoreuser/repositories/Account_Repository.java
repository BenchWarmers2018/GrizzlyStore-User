package com.benchwarmers.grads.grizzlystoreuser.repositories;

import com.benchwarmers.grads.grizzlystoreuser.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface Account_Repository extends JpaRepository<Account, UUID> {
    Account findAccountByAccountEmailAddress(String emailAddress);
    Account findByIdAccount(UUID accountID);
    boolean existsByAccountEmailAddress(String account_EmailAddress);
    boolean existsByIdAccount(UUID accountID);

    @Query(value = "SELECT * FROM account WHERE id_account = ?1", nativeQuery = true)
    Account findByAccountID(UUID id_account);
}