package com.inetkr.cleaner.di

import com.inetkr.cleaner.data.repository.appconfig.AppConfigRepository
import com.inetkr.cleaner.data.repository.appconfig.AppConfigRepositoryImpl
import com.inetkr.cleaner.data.repository.scanFile.ScanFileRepository
import com.inetkr.cleaner.data.repository.scanFile.ScanFileRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract  class RepositoryModule {
    @Binds
    @Singleton
    abstract  fun bindAppConfigRepository(impl: AppConfigRepositoryImpl): AppConfigRepository

   @Binds
   @Singleton
   abstract  fun bindScanFileRepository(impl: ScanFileRepositoryImpl): ScanFileRepository
}