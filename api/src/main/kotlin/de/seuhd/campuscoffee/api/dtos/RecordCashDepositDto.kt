package de.seuhd.campuscoffee.api.dtos

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.DecimalMax
import jakarta.validation.constraints.DecimalMin
import jakarta.validation.constraints.Digits
import jakarta.validation.constraints.NotNull
import java.math.BigDecimal

/** Request to record a cash deposit, with the amount expressed in euros. */
data class RecordCashDepositDto(
    @field:NotNull
    @field:DecimalMin("0.01")
    @field:DecimalMax("1000.00")
    @field:Digits(integer = 4, fraction = 2)
    @field:Schema(description = "Deposit amount in euros, from 0.01 through 1000.00 with at most two decimal places.")
    val amount: BigDecimal?
)
