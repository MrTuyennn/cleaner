package com.inetkr.cleaner.data.repository.appconfig

import com.inetkr.cleaner.data.local.appconfig.DataSourceAppConfig
import com.inetkr.cleaner.di.qualifiers.DefaultDispatcher
import com.inetkr.cleaner.domain.entity.AppConfig
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AppConfigRepositoryImpl @Inject constructor(
    @DefaultDispatcher private val coroutineDispatcher: CoroutineDispatcher,
    private val dataSourceAppConfig: DataSourceAppConfig
) : AppConfigRepository {

    private val _appConfig = MutableStateFlow<AppConfig>(AppConfig())

    override val appConfigInfo: StateFlow<AppConfig> = _appConfig.asStateFlow()

    override suspend fun getAppConfig() = withContext(coroutineDispatcher) {
       val appConfig = dataSourceAppConfig.getAppConfig()
        _appConfig.value = appConfig
    }
}