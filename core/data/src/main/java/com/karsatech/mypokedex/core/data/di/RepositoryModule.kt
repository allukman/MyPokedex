package com.karsatech.mypokedex.core.data.di

import com.karsatech.mypokedex.core.data.repository.PokedexRepositoryImpl
import com.karsatech.mypokedex.core.domain.repository.PokedexRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindPokedexRepository(
        impl: PokedexRepositoryImpl
    ): PokedexRepository
}