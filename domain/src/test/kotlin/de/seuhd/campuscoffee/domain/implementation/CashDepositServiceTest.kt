package de.seuhd.campuscoffee.domain.implementation

import de.seuhd.campuscoffee.domain.exceptions.ForbiddenException
import de.seuhd.campuscoffee.domain.exceptions.ValidationException
import de.seuhd.campuscoffee.domain.model.Role
import de.seuhd.campuscoffee.domain.model.User
import de.seuhd.campuscoffee.domain.ports.data.CashDepositDataService
import de.seuhd.campuscoffee.domain.ports.data.KittyCashBalanceDataService
import de.seuhd.campuscoffee.domain.ports.data.UserDataService
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import org.mockito.kotlin.mock
import org.mockito.kotlin.verifyNoInteractions
import java.util.UUID

/** Verifies that domain guards reject invalid deposits before accessing persistence. */
class CashDepositServiceTest {
    private val users: UserDataService = mock()
    private val kitty: KittyCashBalanceDataService = mock()
    private val deposits: CashDepositDataService = mock()
    private val service = CashDepositServiceImpl(users, kitty, deposits)
    private val recipientId = UUID.randomUUID()
    private val admin =
        User(
            id = UUID.randomUUID(),
            loginName = "admin",
            emailAddress = "admin@example.org",
            firstName = "Coffee",
            lastName = "Admin",
            role = Role.ADMIN
        )

    @ParameterizedTest
    @ValueSource(longs = [-1, 0, 100_001])
    fun `record rejects an out-of-range amount before accessing persistence`(cents: Long) {
        assertThatThrownBy { service.record(recipientId, cents, admin) }.isInstanceOf(ValidationException::class.java)
        verifyNoInteractions(users, kitty, deposits)
    }

    @Test
    fun `record rejects a non-administrator before accessing persistence`() {
        assertThatThrownBy { service.record(recipientId, 1, admin.copy(role = Role.USER)) }
            .isInstanceOf(ForbiddenException::class.java)
        verifyNoInteractions(users, kitty, deposits)
    }
}
