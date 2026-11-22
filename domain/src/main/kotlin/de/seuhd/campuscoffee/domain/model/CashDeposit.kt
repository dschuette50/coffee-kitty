package de.seuhd.campuscoffee.domain.model

import java.time.LocalDateTime
import java.util.UUID

/** A cash deposit credited to [recipient] and recorded by [recordedBy] at [createdAt] in UTC. */
data class CashDeposit(
    override val id: UUID? = null,
    val createdAt: LocalDateTime? = null,
    val updatedAt: LocalDateTime? = null,
    val amountCents: Long,
    val recipient: User,
    val recordedBy: User
) : DomainModel<UUID> {
    companion object {
        /** The smallest deposit, in cents. */
        const val MIN_AMOUNT_CENTS = 1L

        /** The largest deposit, in cents. */
        const val MAX_AMOUNT_CENTS = 100_000L
    }
}
