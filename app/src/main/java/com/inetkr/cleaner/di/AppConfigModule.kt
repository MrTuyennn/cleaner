package com.inetkr.cleaner.di

import com.inetkr.cleaner.data.AppConfigRepository
import com.inetkr.cleaner.data.AppConfigRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract  class AppConfigModule {
    @Binds
    @Singleton
    abstract  fun bindUserRepository(impl: AppConfigRepositoryImpl): AppConfigRepository
}