package com.inetkr.cleaner.data.repository.appconfig

import com.inetkr.cleaner.domain.entity.AppConfig
import kotlinx.coroutines.flow.StateFlow

interface AppConfigRepository {
    suspend fun getAppConfig()

    val appConfigInfo: StateFlow<AppConfig>
}