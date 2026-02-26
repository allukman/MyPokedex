package com.karsatech.mypokedex.core.data.source.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey(autoGenerate = true) val token: Int = 0,
    val name: String,
    val email: String,
    val password: String
)