package de.seuhd.campuscoffee.domain.model

import java.time.LocalDateTime
import java.util.UUID

/** The amount of cash held by the kitty, in cents. */
data class KittyCashBalance(
    override val id: UUID? = null,
    val createdAt: LocalDateTime? = null,
    val updatedAt: LocalDateTime? = null,
    val balanceCents: Long
) : DomainModel<UUID>
