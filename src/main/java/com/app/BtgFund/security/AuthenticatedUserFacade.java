package com.app.BtgFund.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.app.BtgFund.common.UnauthorizedException;
import com.app.BtgFund.service.UserAccountService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
/**
 * Utility component to resolve information from current authenticated user.
 */
public class AuthenticatedUserFacade {

    private final UserAccountService userAccountService;

    /**
     * Resolves current user id from security context.
     *
     * @return current user id
     */
    public String currentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new UnauthorizedException("Unauthorized");
        }

        String email = authentication.getName();
        return userAccountService.getByEmail(email).getId();
    }

    /**
     * Resolves current user email from security context.
     *
     * @return current user email
     */
    public String currentUserEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new UnauthorizedException("Unauthorized");
        }

        return authentication.getName();
    }
}
