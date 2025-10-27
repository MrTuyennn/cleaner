package com.inetkr.cleaner.data.local.appusage

import android.content.Context
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.annotation.RequiresApi
import arrow.core.Either
import com.inetkr.cleaner.domain.entity.AppUsageInfo
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import javax.inject.Inject

class DataSourceAppUsage @Inject constructor(
    @ApplicationContext private val context: Context
) {
    @RequiresApi(Build.VERSION_CODES.R)
    suspend fun getAppUsage(): Either<Throwable, List<AppUsageInfo>> {
        return try {
            val lsAppUsage = mutableListOf<AppUsageInfo>()
            val packageManager = context.packageManager
            val installApplications = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                packageManager.getInstalledApplications(PackageManager.ApplicationInfoFlags.of(0L))
            } else {
                @Suppress("DEPRECATION")
                packageManager.getInstalledApplications(0)
            }
            for (appInfo in installApplications) {
                val appName = packageManager.getApplicationLabel(appInfo).toString()
                val packageName = appInfo.packageName
                val icon = packageManager.getApplicationIcon(appInfo)
                val sourceDir = appInfo.sourceDir
                val size = File(sourceDir).length()
                println("appName: $appName, packageName: $packageName, icon: $icon, size: $size")
                if ((appInfo.flags and ApplicationInfo.FLAG_SYSTEM) == 0) {
                    lsAppUsage.add(
                        AppUsageInfo(
                            appName = appName,
                            packageName = packageName,
                            icon = icon,
                            size = size
                        )
                    )
                }
            }
            Either.Right(lsAppUsage)
        } catch (e: Exception) {
            Either.Left(e)
        }
    }

    suspend fun uninstallApp(appUsage: AppUsageInfo): Either<Throwable, Boolean> {
        return try {
            val intent = Intent(Intent.ACTION_DELETE).apply {
                data = Uri.parse("package:${appUsage.packageName}")
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(intent)
            Either.Right(true)
        } catch (e: Exception) {
            Either.Left(e)
        }
    }

    suspend fun cleanCacheApp(appUsage: AppUsageInfo) {
        try {
            val hasPermission = checkWriteSecureSettingsPermission()
            if(hasPermission) {
                tryCleanCacheDirectly(appUsage.packageName)
            } else {
                openAppSettings(appUsage.packageName)
            }
        } catch (e: Exception) {
            println("Exception : $e")
        }
    }

    private fun checkWriteSecureSettingsPermission(): Boolean {
        return try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                Settings.System.canWrite(context)
            } else {
                true
            }
        } catch (e: Exception) {
            false
        }
    }

    private suspend fun tryCleanCacheDirectly(packageName: String) {
        try {
            val process = Runtime.getRuntime().exec("pm clear --cache-only $packageName")
            val result = process.waitFor()

            if (result == 0) {
                println("Cache cleared successfully for package: $packageName")

            } else {
                println("Cannot clear cache (code: $result)")
            }
        } catch (e: Exception) {
            println("Cannot clear cache Exception : $e")
        }
    }

    private suspend fun openAppSettings(packageName: String) {
         try {
            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                data = Uri.parse("package:$packageName")
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(intent)
        } catch (e: Exception) {
        }
    }
}