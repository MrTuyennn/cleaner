package com.inetkr.cleaner.di

import android.content.Context
import com.inetkr.cleaner.data.local.appconfig.DataSourceAppConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {

    @Provides
    @Singleton
    fun provideDataSourceAppConfig(
        @ApplicationContext context: Context
    ): DataSourceAppConfig = DataSourceAppConfig(context)
}