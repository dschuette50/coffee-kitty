package de.seuhd.campuscoffee.api.mapper

import de.seuhd.campuscoffee.api.dtos.UserDto
import de.seuhd.campuscoffee.domain.model.User
import org.mapstruct.Mapper
import org.mapstruct.Mapping

/**
 * MapStruct mapper between a [User] and a [UserDto]. [toDomain] sets a missing role to `USER` and creates an
 * active user with a zero personal balance.
 */
@Mapper(componentModel = "spring")
interface UserDtoMapper : DtoMapper<User, UserDto> {
    @Mapping(target = "role", source = "role", defaultValue = "USER")
    @Mapping(target = "personalBalanceCents", constant = "0L")
    @Mapping(target = "deactivated", constant = "false")
    override fun toDomain(source: UserDto): User
}
