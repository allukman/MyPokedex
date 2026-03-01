package com.karsatech.mypokedex.core.data.mapper

import com.karsatech.mypokedex.core.data.source.local.model.UserEntity
import com.karsatech.mypokedex.core.domain.model.User

fun UserEntity.toDomain(): User {
    return User(
        id = id,
        name = name,
        email = email,
        password = password
    )
}

fun User.toEntity(): UserEntity {
    return UserEntity(
        id = id,
        name = name,
        email = email,
        password = password
    )
}