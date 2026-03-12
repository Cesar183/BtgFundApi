package com.app.BtgFund.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.app.BtgFund.domain.UserAccount;

public interface UserAccountRepository extends MongoRepository<UserAccount, String> {
    Optional<UserAccount> findByEmail(String email);
}
