package com.app.BtgFund.api;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.BtgFund.api.dto.SubscriptionResponse;
import com.app.BtgFund.security.AuthenticatedUserFacade;
import com.app.BtgFund.service.SubscriptionService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/subscriptions")
@RequiredArgsConstructor
public class SubscriptionController {

    private final SubscriptionService subscriptionService;
    private final AuthenticatedUserFacade authenticatedUserFacade;

    @PostMapping("/{fundId}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public SubscriptionResponse subscribe(@PathVariable String fundId) {
        return subscriptionService.subscribe(authenticatedUserFacade.currentUserId(), fundId);
    }

    @DeleteMapping("/{subscriptionId}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public SubscriptionResponse cancel(@PathVariable String subscriptionId) {
        return subscriptionService.cancel(authenticatedUserFacade.currentUserId(), subscriptionId);
    }

    @GetMapping("/active")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public List<SubscriptionResponse> activeSubscriptions() {
        return subscriptionService.getActiveSubscriptions(authenticatedUserFacade.currentUserId());
    }
}
