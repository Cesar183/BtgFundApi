package com.app.BtgFund.api;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.BtgFund.api.dto.BalanceResponse;
import com.app.BtgFund.security.AuthenticatedUserFacade;
import com.app.BtgFund.service.UserAccountService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/me")
@RequiredArgsConstructor
@Tag(name = "Profile", description = "Operaciones del usuario autenticado")
/**
 * HTTP endpoints for authenticated user profile information.
 */
public class UserController {

    private final AuthenticatedUserFacade authenticatedUserFacade;
    private final UserAccountService userAccountService;

    @GetMapping("/balance")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @Operation(summary = "Consultar saldo", description = "Retorna saldo disponible del usuario autenticado")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Saldo consultado", content = @Content(schema = @Schema(implementation = BalanceResponse.class))),
            @ApiResponse(responseCode = "401", description = "No autenticado")
    })
    /**
     * Returns available balance for current user.
     */
    public BalanceResponse balance() {
        var user = userAccountService.getById(authenticatedUserFacade.currentUserId());
        return new BalanceResponse(user.getAvailableBalance());
    }
}
