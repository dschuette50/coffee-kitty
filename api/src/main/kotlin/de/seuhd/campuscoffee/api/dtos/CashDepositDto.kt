package de.seuhd.campuscoffee.api.dtos

import io.swagger.v3.oas.annotations.media.Schema
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.UUID

/** Details of the recorded cash deposit. The amount is in euros and timestamps are in UTC. */
data class CashDepositDto(
    @field:Schema(accessMode = Schema.AccessMode.READ_ONLY)
    override val id: UUID?,
    @field:Schema(accessMode = Schema.AccessMode.READ_ONLY)
    val createdAt: LocalDateTime?,
    @field:Schema(accessMode = Schema.AccessMode.READ_ONLY)
    val updatedAt: LocalDateTime?,
    val amount: BigDecimal,
    val recipientId: UUID,
    val recordedById: UUID
) : Dto<UUID>
