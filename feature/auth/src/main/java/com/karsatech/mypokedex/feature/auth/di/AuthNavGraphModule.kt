package com.karsatech.mypokedex.feature.auth.di

import com.karsatech.mypokedex.core.navigation.base.BaseNavGraph
import com.karsatech.mypokedex.feature.auth.navigation.AuthNavGraphImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet

@Module
@InstallIn(SingletonComponent::class)
abstract class AuthNavGraphModule {

    @Binds
    @IntoSet
    abstract fun bindHomeNavGraph(navGraph: AuthNavGraphImpl): BaseNavGraph
}