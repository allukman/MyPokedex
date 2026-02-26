package com.karsatech.mypokedex.feature.home.di

import com.karsatech.mypokedex.core.navigation.base.BaseNavGraph
import com.karsatech.mypokedex.feature.home.navigation.HomeNavGraphImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet

@Module
@InstallIn(SingletonComponent::class)
abstract class HomeNavModule {

    @Binds
    @IntoSet
    abstract fun bindHomeNavGraph(navGraph: HomeNavGraphImpl): BaseNavGraph
}