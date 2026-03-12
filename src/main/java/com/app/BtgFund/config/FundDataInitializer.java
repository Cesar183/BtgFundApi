package com.app.BtgFund.config;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.app.BtgFund.domain.Fund;
import com.app.BtgFund.repository.FundRepository;

@Configuration
public class FundDataInitializer {

    @Bean
    CommandLineRunner seedFunds(FundRepository fundRepository) {
        return args -> {
            if (fundRepository.count() > 0) {
                return;
            }

            List<Fund> funds = List.of(
                    Fund.builder().id("1").name("FPV_BTG_PACTUAL_RECAUDADORA").minimumAmount(new BigDecimal("75000"))
                            .category("FPV").build(),
                    Fund.builder().id("2").name("FPV_BTG_PACTUAL_ECOPETROL").minimumAmount(new BigDecimal("125000"))
                            .category("FPV").build(),
                    Fund.builder().id("3").name("DEUDAPRIVADA").minimumAmount(new BigDecimal("50000"))
                            .category("FIC").build(),
                    Fund.builder().id("4").name("FDO-ACCIONES").minimumAmount(new BigDecimal("250000"))
                            .category("FIC").build(),
                    Fund.builder().id("5").name("FPV_BTG_PACTUAL_DINAMICA").minimumAmount(new BigDecimal("100000"))
                            .category("FPV").build());

            fundRepository.saveAll(funds);
        };
    }
}
