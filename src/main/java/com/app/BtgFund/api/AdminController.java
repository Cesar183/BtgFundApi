package com.app.BtgFund.api;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.BtgFund.api.dto.TransactionResponse;
import com.app.BtgFund.service.SubscriptionService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final SubscriptionService subscriptionService;

    @GetMapping("/users/{userId}/transactions")
    @PreAuthorize("hasRole('ADMIN')")
    public List<TransactionResponse> historyByUser(@PathVariable String userId) {
        return subscriptionService.getTransactionHistory(userId);
    }
}
