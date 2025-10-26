package com.inetkr.cleaner.data.local.appusage

import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.os.Build
import arrow.core.Either
import com.inetkr.cleaner.domain.entity.AppUsageInfo
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class DataSourceAppUsage @Inject constructor(
    @ApplicationContext private val context: Context
) {
    suspend fun getAppUsage(): Either<Throwable, List<AppUsageInfo>> {
        return  try {
            val lsAppUsage = mutableListOf<AppUsageInfo>()
            val packageManager = context.packageManager
            val installApplications = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                packageManager.getInstalledApplications(PackageManager.ApplicationInfoFlags.of(0L))
            } else {
                @Suppress("DEPRECATION")
                packageManager.getInstalledApplications(0)
            }
            for (appInfo in installApplications) {
                val appName = packageManager.getApplicationLabel(appInfo).toString()
                val packageName = appInfo.packageName
                val icon = packageManager.getApplicationIcon(appInfo)
                if((appInfo.flags and ApplicationInfo.FLAG_SYSTEM) == 0) {
                    lsAppUsage.add(
                        AppUsageInfo(
                            appName = appName,
                            packageName = packageName,
                            icon = icon)
                    )
                }
            }
            Either.Right(lsAppUsage)
        } catch (e: Exception) {
            Either.Left(e)
        }
    }
}