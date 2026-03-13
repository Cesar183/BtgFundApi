package com.app.BtgFund.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.app.BtgFund.api.dto.FundResponse;
import com.app.BtgFund.repository.FundRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
/**
 * Read-only service for available funds.
 */
public class FundService {

    private final FundRepository fundRepository;

    /**
     * Retrieves all funds and maps them to response DTOs.
     *
     * @return available funds
     */
    public List<FundResponse> getFunds() {
        return fundRepository.findAll().stream()
                .map(fund -> new FundResponse(fund.getId(), fund.getName(), fund.getMinimumAmount(), fund.getCategory()))
                .toList();
    }
}
