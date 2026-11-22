package de.seuhd.campuscoffee.data.persistence.entities

import jakarta.persistence.Column
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table

/** Stores the amount in cents and the two user references. The creation time is the recording time. */
@jakarta.persistence.Entity
@Table(name = "cash_deposits")
class CashDepositEntity : Entity() {
    @field:Column(name = "amount_cents", nullable = false)
    var amountCents: Long = 0

    @field:ManyToOne(optional = false)
    @field:JoinColumn(name = "recipient_id")
    var recipient: UserEntity? = null

    @field:ManyToOne(optional = false)
    @field:JoinColumn(name = "recorded_by_id")
    var recordedBy: UserEntity? = null
}
