package de.seuhd.campuscoffee.api.controller

import de.seuhd.campuscoffee.api.dtos.CashDepositDto
import de.seuhd.campuscoffee.api.dtos.RecordCashDepositDto
import de.seuhd.campuscoffee.api.mapper.CashDepositDtoMapper
import de.seuhd.campuscoffee.api.security.CurrentUserProvider
import de.seuhd.campuscoffee.domain.ports.api.CashDepositService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

/** Records cash deposits under a recipient's user path. */
@RestController
@RequestMapping("/users/{userId}/cash-deposits")
class CashDepositController(
    private val cashDepositService: CashDepositService,
    private val cashDepositDtoMapper: CashDepositDtoMapper,
    private val currentUserProvider: CurrentUserProvider
) {
    /**
     * Records the validated deposit and returns its details.
     *
     * @param userId the recipient's id
     * @param dto the deposit amount in euros
     */
    @Operation(summary = "Record a cash deposit as an administrator.")
    @ApiResponse(responseCode = "201", description = "Cash deposit recorded.")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun record(
        @PathVariable userId: UUID,
        @RequestBody @Valid dto: RecordCashDepositDto
    ): CashDepositDto {
        val amountCents = requireNotNull(dto.amount).movePointRight(2).longValueExact()
        val deposit = cashDepositService.record(userId, amountCents, currentUserProvider.currentUser())
        return cashDepositDtoMapper.toDto(deposit)
    }
}
