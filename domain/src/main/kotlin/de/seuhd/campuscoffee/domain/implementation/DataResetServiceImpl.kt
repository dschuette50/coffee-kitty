package de.seuhd.campuscoffee.domain.implementation

import de.seuhd.campuscoffee.domain.ports.data.CashDepositDataService
import de.seuhd.campuscoffee.domain.ports.data.CoffeeConsumptionDataService
import de.seuhd.campuscoffee.domain.ports.data.KittyCashBalanceDataService
import de.seuhd.campuscoffee.domain.ports.data.UserDataService
import de.seuhd.campuscoffee.domain.ports.internal.DataResetService
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.stereotype.Service

/**
 * Clears cash deposits, the kitty cash balance, and consumptions before deleting users.
 * Deposits and consumptions reference users.
 */
@Service
class DataResetServiceImpl(
    private val coffeeConsumptionDataService: CoffeeConsumptionDataService,
    private val userDataService: UserDataService,
    private val cashDepositDataService: CashDepositDataService,
    private val kittyCashBalanceDataService: KittyCashBalanceDataService
) : DataResetService {
    override fun clearAll() {
        log.warn { "Clearing all data." }
        cashDepositDataService.clear()
        kittyCashBalanceDataService.clear()
        coffeeConsumptionDataService.clear()
        userDataService.clear()
    }

    private companion object {
        private val log = KotlinLogging.logger {}
    }
}
