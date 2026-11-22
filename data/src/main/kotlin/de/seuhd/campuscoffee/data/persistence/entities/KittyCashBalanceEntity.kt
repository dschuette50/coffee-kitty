package de.seuhd.campuscoffee.data.persistence.entities

import jakarta.persistence.Column
import jakarta.persistence.Table

/** Stores the singleton cash balance, constrained to one fixed key and a nonnegative amount in cents. */
@jakarta.persistence.Entity
@Table(name = "kitty_cash_balance")
class KittyCashBalanceEntity : Entity() {
    @field:Column(name = "balance_cents", nullable = false)
    var balanceCents: Long = 0
}
