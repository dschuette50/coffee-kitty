package de.seuhd.campuscoffee.domain.ports.data

import de.seuhd.campuscoffee.domain.model.CashDeposit
import java.util.UUID

/** Data port for cash deposit records. */
interface CashDepositDataService : CrudDataService<CashDeposit, UUID>
