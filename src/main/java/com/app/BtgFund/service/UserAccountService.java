package com.app.BtgFund.service;

import org.springframework.stereotype.Service;

import com.app.BtgFund.common.ResourceNotFoundException;
import com.app.BtgFund.domain.UserAccount;
import com.app.BtgFund.repository.UserAccountRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserAccountService {

    private final UserAccountRepository userAccountRepository;

    public UserAccount getByEmail(String email) {
        return userAccountRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    public UserAccount getById(String id) {
        return userAccountRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }
}
