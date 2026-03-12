package com.app.BtgFund.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.app.BtgFund.domain.FundTransaction;

public interface FundTransactionRepository extends MongoRepository<FundTransaction, String> {
    List<FundTransaction> findByUserIdOrderByCreatedAtDesc(String userId);
}
