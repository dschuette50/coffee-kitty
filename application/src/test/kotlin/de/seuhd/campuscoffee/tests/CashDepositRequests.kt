package de.seuhd.campuscoffee.tests

import de.seuhd.campuscoffee.api.dtos.CashDepositDto
import de.seuhd.campuscoffee.api.security.CurrentUserProvider
import de.seuhd.campuscoffee.tests.ConsumptionRequests.ADMIN_LOGIN_NAME
import de.seuhd.campuscoffee.tests.SystemTestUtils.client
import org.springframework.http.MediaType
import java.util.UUID

/** Sends requests to record cash deposits in system tests. */
object CashDepositRequests {
    fun record(
        recipientId: UUID,
        amount: String,
        actingUser: String? = ADMIN_LOGIN_NAME
    ): Response<CashDepositDto> =
        client()
            .post()
            .uri("/api/users/{id}/cash-deposits", recipientId)
            .contentType(MediaType.APPLICATION_JSON)
            .headers { headers -> actingUser?.let { headers.set(CurrentUserProvider.ACTING_USER_HEADER, it) } }
            .body("""{"amount": $amount}""")
            .exchange()
            .toResponse()
}
