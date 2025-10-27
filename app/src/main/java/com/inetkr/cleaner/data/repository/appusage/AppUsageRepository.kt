package com.inetkr.cleaner.data.repository.appusage

import arrow.core.Either
import com.inetkr.cleaner.domain.entity.AppUsageInfo

interface AppUsageRepository {
    suspend fun getAppUsage(): Either<Throwable, List<AppUsageInfo>>
    suspend fun unInstallApp(appUsage: AppUsageInfo): Either<Throwable, Boolean>

    suspend fun cleanCacheApp(appUsage: AppUsageInfo)
}