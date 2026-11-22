package de.seuhd.campuscoffee.api.mapper

import de.seuhd.campuscoffee.api.dtos.CashDepositDto
import de.seuhd.campuscoffee.domain.model.CashDeposit
import org.springframework.stereotype.Component
import java.math.BigDecimal

/** Converts the recorded deposit to its API representation in euros. */
@Component
class CashDepositDtoMapper {
    /**
     * Maps a persisted deposit to the response body.
     *
     * @param deposit the persisted deposit
     */
    fun toDto(deposit: CashDeposit): CashDepositDto =
        CashDepositDto(
            id = deposit.id,
            createdAt = deposit.createdAt,
            updatedAt = deposit.updatedAt,
            amount = BigDecimal.valueOf(deposit.amountCents, 2),
            recipientId = requireNotNull(deposit.recipient.id),
            recordedById = requireNotNull(deposit.recordedBy.id)
        )
}
