package com.app.BtgFund.api;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.BtgFund.api.dto.FundResponse;
import com.app.BtgFund.service.FundService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/funds")
@RequiredArgsConstructor
@Tag(name = "Funds", description = "Consulta de fondos disponibles")
/**
 * HTTP endpoints to query fund catalog.
 */
public class FundController {

    private final FundService fundService;

    @GetMapping
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @Operation(summary = "Listar fondos", description = "Retorna fondos disponibles para suscripcion")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Listado de fondos", content = @Content(array = @ArraySchema(schema = @Schema(implementation = FundResponse.class)))),
            @ApiResponse(responseCode = "401", description = "No autenticado")
    })
    /**
     * Returns all available funds for subscription.
     */
    public List<FundResponse> listFunds() {
        return fundService.getFunds();
    }
}
