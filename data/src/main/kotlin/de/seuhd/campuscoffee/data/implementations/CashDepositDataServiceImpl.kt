package de.seuhd.campuscoffee.data.implementations

import de.seuhd.campuscoffee.data.mapper.CashDepositEntityMapper
import de.seuhd.campuscoffee.data.persistence.entities.CashDepositEntity
import de.seuhd.campuscoffee.data.persistence.repositories.CashDepositRepository
import de.seuhd.campuscoffee.domain.model.CashDeposit
import de.seuhd.campuscoffee.domain.ports.data.CashDepositDataService
import org.springframework.stereotype.Service
import java.util.UUID

/** PostgreSQL adapter for the [CashDepositDataService] port. */
@Service
class CashDepositDataServiceImpl(
    repository: CashDepositRepository,
    entityMapper: CashDepositEntityMapper
) : CrudDataServiceImpl<CashDeposit, CashDepositEntity, CashDepositRepository, UUID>(
        repository,
        entityMapper,
        CashDeposit::class.java
    ),
    CashDepositDataService
