package com.example.tipjar.di

import com.example.tipjar.data.repository.TipRepository
import com.example.tipjar.data.repository.TipRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Module
    @InstallIn(SingletonComponent::class)
    interface AppModuleInt {

        @Binds
        fun bindUserDataRepository(authRepo: TipRepositoryImpl): TipRepository
    }
}