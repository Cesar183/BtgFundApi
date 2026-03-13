package com.app.BtgFund.service;

import org.springframework.stereotype.Service;

import com.app.BtgFund.common.ResourceNotFoundException;
import com.app.BtgFund.domain.UserAccount;
import com.app.BtgFund.repository.UserAccountRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
/**
 * Service for user-account lookup operations.
 */
public class UserAccountService {

    private final UserAccountRepository userAccountRepository;

    /**
     * Finds user by email.
     *
     * @param email unique user email
     * @return matching user account
     */
    public UserAccount getByEmail(String email) {
        return userAccountRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    /**
     * Finds user by id.
     *
     * @param id user id
     * @return matching user account
     */
    public UserAccount getById(String id) {
        return userAccountRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }
}
