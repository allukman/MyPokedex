package com.karsatech.mypokedex.core.data.di

import android.content.Context
import androidx.room.Room
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.karsatech.mypokedex.core.data.BuildConfig.DEBUG
import com.karsatech.mypokedex.core.data.repository.PokedexRepository
import com.karsatech.mypokedex.core.data.repository.PokedexRepositoryImpl
import com.karsatech.mypokedex.core.data.source.local.datastore.PokedexDataStore
import com.karsatech.mypokedex.core.data.source.local.db.PokedexDatabase
import com.karsatech.mypokedex.core.data.source.remote.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level.BODY
import okhttp3.logging.HttpLoggingInterceptor.Level.NONE
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
class DataModule {
    @Provides
    fun provideRepositoryPokedex(
        apiService: ApiService,
        db: PokedexDatabase
    ): PokedexRepository = PokedexRepositoryImpl(apiService, db)

    @Provides
    fun provideApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)

    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit = Retrofit.Builder()
        .baseUrl("https://pokeapi.co/api/v2/")
        .addConverterFactory(gsonConverterFactory)
        .client(okHttpClient)
        .build()

    @Provides
    fun provideOkHttpClient(
        chuckerInterceptor: ChuckerInterceptor
    ): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().setLevel(if (DEBUG) BODY else NONE))
        .addInterceptor(chuckerInterceptor)
        .build()

    @Provides
    fun provideGsonConverterFactory(): GsonConverterFactory = GsonConverterFactory.create()

    @Provides
    fun provideChuckerInterceptor(
        @ApplicationContext context: Context
    ) = ChuckerInterceptor.Builder(context).collector(ChuckerCollector(context)).build()

    @Provides
    fun provideAppDatabase(
        @ApplicationContext context: Context
    ): PokedexDatabase = Room.databaseBuilder(
        context,
        PokedexDatabase::class.java,
        "app_database"
    ).fallbackToDestructiveMigration(true).build()

    @Provides
    fun provideUserDao(pokedexDatabase: PokedexDatabase) = pokedexDatabase.userDao()

    @Provides
    fun providePokemonDao(pokedexDatabase: PokedexDatabase) = pokedexDatabase.pokemonDao()

    @Provides
    fun provideRemoteKeysDao(pokedexDatabase: PokedexDatabase) = pokedexDatabase.remoteKeysDao()

    @Provides
    fun provideDataStore(@ApplicationContext context: Context) = PokedexDataStore(context)
}