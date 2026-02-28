package com.karsatech.mypokedex.core.data.source.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.karsatech.mypokedex.core.data.source.local.model.PokemonEntity
import com.karsatech.mypokedex.core.data.source.local.model.RemoteKeysEntity
import com.karsatech.mypokedex.core.data.source.local.model.UserEntity
import com.karsatech.mypokedex.core.data.util.Converter

@Database(
    entities = [UserEntity::class, PokemonEntity::class, RemoteKeysEntity::class],
    exportSchema = false,
    version = 1
)
@TypeConverters(Converter::class)
abstract class PokedexDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun pokemonDao(): PokemonDao
    abstract fun remoteKeysDao(): RemoteKeysDao
}
