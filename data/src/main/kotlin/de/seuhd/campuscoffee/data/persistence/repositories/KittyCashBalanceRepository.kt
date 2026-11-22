package de.seuhd.campuscoffee.data.persistence.repositories

import de.seuhd.campuscoffee.data.persistence.entities.KittyCashBalanceEntity
import jakarta.persistence.LockModeType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import java.time.LocalDateTime
import java.util.UUID

/** Initializes and locks the singleton cash balance in the caller's transaction. */
interface KittyCashBalanceRepository : JpaRepository<KittyCashBalanceEntity, UUID> {
    /**
     * Inserts the opening cash balance if the singleton row does not yet exist.
     *
     * @param id the singleton key
     * @param now the current UTC timestamp
     */
    @Modifying
    @Query(
        value =
            "INSERT INTO kitty_cash_balance (id, created_at, updated_at) VALUES (:id, :now, :now) " +
                "ON CONFLICT (id) DO NOTHING",
        nativeQuery = true
    )
    fun initialize(
        id: UUID,
        now: LocalDateTime
    )

    /**
     * Loads and locks the singleton until the transaction ends.
     *
     * @param id the singleton key
     */
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    fun findForUpdateById(id: UUID): KittyCashBalanceEntity?
}
