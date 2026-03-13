package com.app.BtgFund.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.app.BtgFund.domain.UserAccount;

/**
 * Mongo repository for user accounts.
 */
public interface UserAccountRepository extends MongoRepository<UserAccount, String> {
    /**
     * Finds user by unique email.
     *
     * @param email user email
     * @return optional user account
     */
    Optional<UserAccount> findByEmail(String email);
}
