package de.seuhd.campuscoffee.domain.ports.data

import de.seuhd.campuscoffee.domain.model.KittyCashBalance
import java.util.UUID

/** Data port for the singleton kitty cash balance. */
interface KittyCashBalanceDataService : CrudDataService<KittyCashBalance, UUID> {
    /** Initializes the cash balance if needed and locks its row in the caller's transaction. */
    fun getForUpdate(): KittyCashBalance
}
