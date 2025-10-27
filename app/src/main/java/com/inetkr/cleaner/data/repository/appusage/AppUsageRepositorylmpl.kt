package com.inetkr.cleaner.data.repository.appusage

import android.os.Build
import androidx.annotation.RequiresApi
import arrow.core.Either
import com.inetkr.cleaner.data.local.appusage.DataSourceAppUsage
import com.inetkr.cleaner.di.qualifiers.DefaultDispatcher
import com.inetkr.cleaner.domain.entity.AppUsageInfo
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AppUsageRepositoryImpl @Inject constructor(
    @DefaultDispatcher private val coroutineDispatcher: CoroutineDispatcher,
    private val dataSourceAppUsage: DataSourceAppUsage
) : AppUsageRepository {
    @RequiresApi(Build.VERSION_CODES.R)
    override suspend fun getAppUsage(): Either<Throwable, List<AppUsageInfo>> =
        withContext(coroutineDispatcher) {
            return@withContext dataSourceAppUsage.getAppUsage()
        }

    override suspend fun unInstallApp(appUsage: AppUsageInfo): Either<Throwable, Boolean> =
        withContext(coroutineDispatcher) {
            return@withContext dataSourceAppUsage.uninstallApp(appUsage)
        }
}