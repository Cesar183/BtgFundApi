package com.app.BtgFund.api;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.BtgFund.api.dto.BalanceResponse;
import com.app.BtgFund.security.AuthenticatedUserFacade;
import com.app.BtgFund.service.UserAccountService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/me")
@RequiredArgsConstructor
public class UserController {

    private final AuthenticatedUserFacade authenticatedUserFacade;
    private final UserAccountService userAccountService;

    @GetMapping("/balance")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public BalanceResponse balance() {
        var user = userAccountService.getById(authenticatedUserFacade.currentUserId());
        return new BalanceResponse(user.getAvailableBalance());
    }
}
