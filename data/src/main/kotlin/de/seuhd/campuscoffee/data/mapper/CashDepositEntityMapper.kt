package de.seuhd.campuscoffee.data.mapper

import de.seuhd.campuscoffee.data.persistence.entities.CashDepositEntity
import de.seuhd.campuscoffee.domain.model.CashDeposit
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.MappingTarget

/** Maps CashDeposit between the domain and persistence and preserves stored ids and timestamps on updates. */
@Mapper(componentModel = "spring", uses = [UserEntityMapper::class, EntityReferenceResolver::class])
interface CashDepositEntityMapper : EntityMapper<CashDeposit, CashDepositEntity> {
    @Mapping(target = "recipient", source = "recipient.id")
    @Mapping(target = "recordedBy", source = "recordedBy.id")
    override fun toEntity(source: CashDeposit): CashDepositEntity

    @Mapping(target = "recipient", source = "recipient.id")
    @Mapping(target = "recordedBy", source = "recordedBy.id")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    override fun updateEntity(
        source: CashDeposit,
        @MappingTarget target: CashDepositEntity
    )
}
