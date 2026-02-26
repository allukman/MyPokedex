package com.karsatech.mypokedex.core.data.source.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.karsatech.mypokedex.core.data.source.local.model.UserEntity
import com.karsatech.mypokedex.core.data.util.Converter

@Database(
    entities = [UserEntity::class],
    exportSchema = false,
    version = 1
)
@TypeConverters(Converter::class)
abstract class PokedexDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}
