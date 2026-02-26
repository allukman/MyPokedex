package com.karsatech.mypokedex.feature.profile.di

import com.karsatech.mypokedex.core.navigation.base.BaseNavGraph
import com.karsatech.mypokedex.feature.profile.navigation.ProfileNavGraphImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet

@Module
@InstallIn(SingletonComponent::class)
abstract class ProfileNavGraphModule {

    @Binds
    @IntoSet
    abstract fun bindProfileNavGraph(impl: ProfileNavGraphImpl): BaseNavGraph
}