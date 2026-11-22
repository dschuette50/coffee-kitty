package de.seuhd.campuscoffee.domain.ports.api

import de.seuhd.campuscoffee.domain.model.CashDeposit
import de.seuhd.campuscoffee.domain.model.User
import java.util.UUID

/** Operations for recording cash received from members. */
interface CashDepositService {
    /**
     * Records a cash deposit and credits both balances in one transaction.
     *
     * @param userId the recipient's id
     * @param amountCents the amount in cents
     * @param actingUser the administrator recording the deposit
     */
    fun record(
        userId: UUID,
        amountCents: Long,
        actingUser: User
    ): CashDeposit
}
