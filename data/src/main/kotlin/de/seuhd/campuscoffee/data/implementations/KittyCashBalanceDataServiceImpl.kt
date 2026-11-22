package de.seuhd.campuscoffee.data.implementations

import de.seuhd.campuscoffee.data.mapper.KittyCashBalanceEntityMapper
import de.seuhd.campuscoffee.data.persistence.entities.KittyCashBalanceEntity
import de.seuhd.campuscoffee.data.persistence.repositories.KittyCashBalanceRepository
import de.seuhd.campuscoffee.domain.model.KittyCashBalance
import de.seuhd.campuscoffee.domain.ports.data.KittyCashBalanceDataService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.temporal.ChronoUnit
import java.util.UUID

/** PostgreSQL adapter for the [KittyCashBalanceDataService] port. */
@Service
class KittyCashBalanceDataServiceImpl(
    repository: KittyCashBalanceRepository,
    entityMapper: KittyCashBalanceEntityMapper
) : CrudDataServiceImpl<KittyCashBalance, KittyCashBalanceEntity, KittyCashBalanceRepository, UUID>(
        repository,
        entityMapper,
        KittyCashBalance::class.java
    ),
    KittyCashBalanceDataService {
    @Transactional(propagation = Propagation.MANDATORY)
    override fun getForUpdate(): KittyCashBalance {
        repository.initialize(KITTY_ID, LocalDateTime.now(ZoneOffset.UTC).truncatedTo(ChronoUnit.MICROS))
        val entity = checkNotNull(repository.findForUpdateById(KITTY_ID))
        return mapper.fromEntity(entity)
    }

    private companion object {
        private val KITTY_ID = UUID.fromString("00000000-0000-0000-0000-000000000001")
    }
}
