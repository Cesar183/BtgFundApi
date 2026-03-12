package com.app.BtgFund.api;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.BtgFund.api.dto.FundResponse;
import com.app.BtgFund.service.FundService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/funds")
@RequiredArgsConstructor
public class FundController {

    private final FundService fundService;

    @GetMapping
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public List<FundResponse> listFunds() {
        return fundService.getFunds();
    }
}
