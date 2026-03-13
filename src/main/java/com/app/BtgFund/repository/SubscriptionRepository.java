package com.app.BtgFund.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.app.BtgFund.domain.Subscription;
import com.app.BtgFund.domain.SubscriptionStatus;

/**
 * Mongo repository for subscription records.
 */
public interface SubscriptionRepository extends MongoRepository<Subscription, String> {
    /**
     * Finds subscription by id scoped to a user.
     */
    Optional<Subscription> findByIdAndUserId(String id, String userId);

    /**
     * Finds active subscription for a specific user and fund.
     */
    Optional<Subscription> findByUserIdAndFundIdAndStatus(String userId, String fundId, SubscriptionStatus status);

    /**
     * Lists subscriptions by user and status.
     */
    List<Subscription> findByUserIdAndStatus(String userId, SubscriptionStatus status);
}
