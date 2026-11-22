package de.seuhd.campuscoffee.tests.system

import de.seuhd.campuscoffee.domain.model.Role
import de.seuhd.campuscoffee.domain.ports.data.CashDepositDataService
import de.seuhd.campuscoffee.domain.ports.data.KittyCashBalanceDataService
import de.seuhd.campuscoffee.domain.ports.data.UserDataService
import de.seuhd.campuscoffee.tests.CashDepositRequests.record
import de.seuhd.campuscoffee.tests.ConsumptionRequests.ADMIN_LOGIN_NAME
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import java.math.BigDecimal
import java.util.UUID
import java.util.concurrent.CountDownLatch
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import javax.sql.DataSource

/** Verifies the deposit API and its committed PostgreSQL state. */
class CashDepositSystemTests : AbstractSystemTest() {
    @Autowired
    private lateinit var users: UserDataService

    @Autowired
    private lateinit var kitty: KittyCashBalanceDataService

    @Autowired
    private lateinit var deposits: CashDepositDataService

    @Autowired
    private lateinit var dataSource: DataSource

    private val member get() = fixtureUsers.first { it.role == Role.USER }
    private val memberId get() = requireNotNull(member.id)

    @Test
    fun `recording ten euros increases both balances and persists the deposit details`() {
        users.upsert(member.copy(personalBalanceCents = -400))
        executeSql(
            "INSERT INTO kitty_cash_balance (id, created_at, updated_at, balance_cents) " +
                "VALUES ('00000000-0000-0000-0000-000000000001', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 2000)"
        )

        val response = record(memberId, "10.00")

        assertThat(response.status).isEqualTo(HttpStatus.CREATED.value())
        assertThat(response.headers.location).isNull()
        val body = response.body
        val stored = deposits.getAll().single()
        assertThat(body.id).isEqualTo(stored.id)
        assertThat(body.amount).isEqualTo(BigDecimal("10.00"))
        assertThat(body.recipientId).isEqualTo(memberId)
        assertThat(body.recordedById).isEqualTo(fixtureUser(ADMIN_LOGIN_NAME).id)
        assertThat(body.createdAt).isNotNull().isEqualTo(stored.createdAt)
        assertThat(stored.amountCents).isEqualTo(1_000L)
        assertThat(stored.recipient.id).isEqualTo(body.recipientId)
        assertThat(stored.recordedBy.id).isEqualTo(body.recordedById)
        assertBalances(memberId, 600, 3_000)
    }

    @ParameterizedTest
    @ValueSource(strings = ["0.01", "1000.00"])
    fun `recording a boundary amount initializes the kitty and credits both balances`(amount: String) {
        assertThat(kitty.getAll()).isEmpty()
        assertThat(users.getById(memberId).personalBalanceCents).isZero()

        val response = record(memberId, amount)

        assertThat(response.status).isEqualTo(HttpStatus.CREATED.value())
        val cents = BigDecimal(amount).movePointRight(2).longValueExact()
        assertBalances(memberId, cents, cents)
        assertThat(deposits.getAll().single().amountCents).isEqualTo(cents)
    }

    @ParameterizedTest
    @ValueSource(strings = ["-0.01", "0", "1000.01", "12.501", "12.500"])
    fun `recording an invalid amount returns 400 and writes nothing`(amount: String) {
        assertThat(record(memberId, amount).status).isEqualTo(HttpStatus.BAD_REQUEST.value())
        assertEmptyDepositState()
    }

    @Test
    fun `recording without administrator authorization returns 401 or 403 and writes nothing`() {
        assertThat(record(memberId, "null", null).status).isEqualTo(HttpStatus.UNAUTHORIZED.value())
        assertThat(record(memberId, "1.00", "unknown_login").status).isEqualTo(HttpStatus.UNAUTHORIZED.value())
        assertThat(record(memberId, "1.00", member.loginName).status).isEqualTo(HttpStatus.FORBIDDEN.value())
        assertEmptyDepositState()
    }

    @Test
    fun `recording for a deactivated member credits both balances`() {
        users.upsert(member.copy(deactivated = true, personalBalanceCents = -200))

        assertThat(record(memberId, "1.00").status).isEqualTo(HttpStatus.CREATED.value())

        assertBalances(memberId, -100, 100)
        assertThat(users.getById(memberId).deactivated).isTrue()
        val deposit = deposits.getAll().single()
        assertThat(deposit.recipient.id).isEqualTo(memberId)
    }

    @Test
    fun `a failed deposit insert rolls back the balances and first-use initialization`() {
        executeSql("ALTER TABLE cash_deposits ADD CONSTRAINT test_deposit_failure CHECK (amount_cents <> 123)")
        try {
            assertThat(record(memberId, "1.23").status).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.value())
            assertEmptyDepositState()
        } finally {
            executeSql("ALTER TABLE cash_deposits DROP CONSTRAINT test_deposit_failure")
        }
    }

    @Test
    fun `concurrent deposits credit the same and different recipients without lost updates`() {
        val otherId = requireNotNull(fixtureUser(ADMIN_LOGIN_NAME).id)
        val recipients = listOf(memberId, otherId, memberId, otherId)
        val start = CountDownLatch(1)

        Executors.newFixedThreadPool(recipients.size).use { executor ->
            val results =
                recipients.map { recipient ->
                    executor.submit<Int> {
                        check(start.await(10, TimeUnit.SECONDS))
                        record(recipient, "1.25").status
                    }
                }
            start.countDown()
            assertThat(results.map { it.get(20, TimeUnit.SECONDS) }).containsOnly(HttpStatus.CREATED.value())
        }

        assertBalances(memberId, 250, 500)
        assertBalances(otherId, 250, 500)
        assertThat(deposits.getAll().map { it.recipient.id }).containsExactlyInAnyOrderElementsOf(recipients)
    }

    private fun executeSql(sql: String) {
        dataSource.connection.use { connection ->
            connection.createStatement().use { it.executeUpdate(sql) }
        }
    }

    private fun assertEmptyDepositState() {
        assertThat(users.getById(memberId).personalBalanceCents).isZero()
        assertThat(kitty.getAll()).isEmpty()
        assertThat(deposits.getAll()).isEmpty()
    }

    private fun assertBalances(
        recipientId: UUID,
        personalCents: Long,
        cashCents: Long
    ) {
        assertThat(users.getById(recipientId).personalBalanceCents).isEqualTo(personalCents)
        assertThat(kitty.getAll().single().balanceCents).isEqualTo(cashCents)
    }
}
