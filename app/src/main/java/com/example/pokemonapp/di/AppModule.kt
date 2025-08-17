package com.example.pokemonapp.di

import android.content.Context
import androidx.room.Room
import com.example.pokemonapp.data.PokemonApi
import com.example.pokemonapp.data.PokemonLocalDataSource
import com.example.pokemonapp.data.PokemonLocalDataSourceImpl
import com.example.pokemonapp.data.PokemonRemoteDataSource
import com.example.pokemonapp.data.PokemonRemoteDataSourceImpl
import com.example.pokemonapp.data.PokemonRepository
import com.example.pokemonapp.data.PokemonRepositoryImpl
import com.example.pokemonapp.data.RetrofitHelper
import com.example.pokemonapp.data.db.AppDatabase
import dagger.Binds
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Singleton

@Module(includes = [AppModuleProvides::class])
interface AppModule {

    @Binds
    @Singleton
    fun bindsLocalDataSource(impl: PokemonLocalDataSourceImpl): PokemonLocalDataSource

    @Binds
    @Singleton
    fun bindsRemoteDataSource(impl: PokemonRemoteDataSourceImpl): PokemonRemoteDataSource

    @Binds
    @Singleton
    fun bindsRepository(impl: PokemonRepositoryImpl): PokemonRepository
}

@Module
class AppModuleProvides {

    @Provides
    @Singleton
    fun providesRetrofit() = RetrofitHelper.getInstance()

    @Provides
    @Singleton
    fun providesPokemonApi(retrofit: Retrofit) = retrofit.create<PokemonApi>()

    @Provides
    @Singleton
    fun providesAppDatabase(context: Context) = Room
        .databaseBuilder(context,AppDatabase::class.java, "pokemon-db")
        .build()

    @Provides
    @Singleton
    fun providesPokemonDao(db: AppDatabase) = db.pokemonDao()
}