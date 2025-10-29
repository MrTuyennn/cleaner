package com.inetkr.cleaner.di

import com.inetkr.cleaner.data.repository.appconfig.AppConfigRepository
import com.inetkr.cleaner.data.repository.appconfig.AppConfigRepositoryImpl
import com.inetkr.cleaner.data.repository.appusage.AppUsageRepository
import com.inetkr.cleaner.data.repository.appusage.AppUsageRepositoryImpl
import com.inetkr.cleaner.data.repository.memorymanager.MemoryManagerRepository
import com.inetkr.cleaner.data.repository.memorymanager.MemoryManagerRepositoryImpl
import com.inetkr.cleaner.data.repository.scanFile.ScanFileRepository
import com.inetkr.cleaner.data.repository.scanFile.ScanFileRepositoryImpl
import com.inetkr.cleaner.data.repository.scanner.ScannerRepository
import com.inetkr.cleaner.data.repository.scanner.ScannerRepositoryImpl
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

   @Binds
   @Singleton
   abstract fun bindAppUsageRepository(impl: AppUsageRepositoryImpl): AppUsageRepository

   @Binds
   @Singleton
   abstract fun bindMemoryManagerRepository(impl: MemoryManagerRepositoryImpl): MemoryManagerRepository

   @Binds
   @Singleton
   abstract fun bindScannerRepository(impl: ScannerRepositoryImpl): ScannerRepository
}