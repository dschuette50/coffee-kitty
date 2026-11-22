package de.seuhd.campuscoffee.data.persistence.repositories

import de.seuhd.campuscoffee.data.persistence.entities.CashDepositEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

/** Spring Data repository for recorded cash deposits. */
interface CashDepositRepository : JpaRepository<CashDepositEntity, UUID>
