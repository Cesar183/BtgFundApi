package com.app.BtgFund.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.app.BtgFund.domain.FundTransaction;

/**
 * Mongo repository for fund transactions.
 */
public interface FundTransactionRepository extends MongoRepository<FundTransaction, String> {
    /**
     * Lists transactions for a user sorted by newest creation date.
     */
    List<FundTransaction> findByUserIdOrderByCreatedAtDesc(String userId);
}
