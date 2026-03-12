package com.app.BtgFund.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.app.BtgFund.domain.Subscription;
import com.app.BtgFund.domain.SubscriptionStatus;

public interface SubscriptionRepository extends MongoRepository<Subscription, String> {
    Optional<Subscription> findByIdAndUserId(String id, String userId);

    Optional<Subscription> findByUserIdAndFundIdAndStatus(String userId, String fundId, SubscriptionStatus status);

    List<Subscription> findByUserIdAndStatus(String userId, SubscriptionStatus status);
}
