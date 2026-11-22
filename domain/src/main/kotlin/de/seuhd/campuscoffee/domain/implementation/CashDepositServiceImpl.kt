package de.seuhd.campuscoffee.domain.implementation

import de.seuhd.campuscoffee.domain.exceptions.ValidationException
import de.seuhd.campuscoffee.domain.model.CashDeposit
import de.seuhd.campuscoffee.domain.model.User
import de.seuhd.campuscoffee.domain.ports.api.CashDepositService
import de.seuhd.campuscoffee.domain.ports.data.CashDepositDataService
import de.seuhd.campuscoffee.domain.ports.data.KittyCashBalanceDataService
import de.seuhd.campuscoffee.domain.ports.data.UserDataService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

/** Records a cash deposit after locking the recipient and then the shared kitty cash balance. */
@Service
class CashDepositServiceImpl(
    private val users: UserDataService,
    private val cashBalance: KittyCashBalanceDataService,
    private val deposits: CashDepositDataService
) : CashDepositService {
    @Transactional
    override fun record(
        userId: UUID,
        amountCents: Long,
        actingUser: User
    ): CashDeposit {
        requireAdmin(actingUser, "record a cash deposit")
        if (amountCents !in CashDeposit.MIN_AMOUNT_CENTS..CashDeposit.MAX_AMOUNT_CENTS) {
            throw ValidationException("The deposit amount must be between €0.01 and €1,000.00.")
        }

        val recipient = users.getByIdForUpdate(userId)
        val kitty = cashBalance.getForUpdate()
        val personalBalance = Math.addExact(recipient.personalBalanceCents, amountCents)
        val creditedRecipient = users.upsert(recipient.copy(personalBalanceCents = personalBalance))
        cashBalance.upsert(kitty.copy(balanceCents = Math.addExact(kitty.balanceCents, amountCents)))
        return deposits.upsert(
            CashDeposit(amountCents = amountCents, recipient = creditedRecipient, recordedBy = actingUser)
        )
    }
}
