package com.app.BtgFund.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.app.BtgFund.domain.Fund;

/**
 * Mongo repository for fund catalog.
 */
public interface FundRepository extends MongoRepository<Fund, String> {
}
