package de.seuhd.campuscoffee.data.mapper

import de.seuhd.campuscoffee.data.persistence.entities.KittyCashBalanceEntity
import de.seuhd.campuscoffee.domain.model.KittyCashBalance
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.MappingTarget

/** Maps KittyCashBalance between the domain and persistence and preserves stored ids and timestamps on updates. */
@Mapper(componentModel = "spring")
interface KittyCashBalanceEntityMapper : EntityMapper<KittyCashBalance, KittyCashBalanceEntity> {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    override fun updateEntity(
        source: KittyCashBalance,
        @MappingTarget target: KittyCashBalanceEntity
    )
}
