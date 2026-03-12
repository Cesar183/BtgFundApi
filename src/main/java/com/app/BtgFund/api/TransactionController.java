package com.app.BtgFund.api;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.BtgFund.api.dto.TransactionResponse;
import com.app.BtgFund.security.AuthenticatedUserFacade;
import com.app.BtgFund.service.SubscriptionService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final SubscriptionService subscriptionService;
    private final AuthenticatedUserFacade authenticatedUserFacade;

    @GetMapping
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public List<TransactionResponse> history() {
        return subscriptionService.getTransactionHistory(authenticatedUserFacade.currentUserId());
    }
}
