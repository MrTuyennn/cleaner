package com.inetkr.cleaner.data.local.appconfig

import android.app.ActivityManager
import android.content.Context
import android.os.Build
import com.inetkr.cleaner.BuildConfig
import com.inetkr.cleaner.domain.entity.AppConfig
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.Locale
import java.util.TimeZone
import java.util.UUID

class DataSourceAppConfig(
    @ApplicationContext private val context: Context
) {
    suspend fun getAppConfig(): AppConfig {
        val activityManager: ActivityManager =
            context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val packageManager = context.packageManager
        val info = packageManager.getPackageInfo(context.packageName, 0)
        val id = generateDeviceFingerprint()
        val language = Locale.getDefault().language
        val diaCode = Locale.getDefault().country
        val memoryInfo: ActivityManager.MemoryInfo = ActivityManager.MemoryInfo()
        activityManager.getMemoryInfo(memoryInfo)

        val appConfig = AppConfig(
            displayName = info.applicationInfo?.loadLabel(packageManager).toString(),
            bundleName = context.packageName,
            versionNumber = BuildConfig.VERSION_NAME.toString(),
            buildNumber = BuildConfig.VERSION_CODE.toString(),
            uuid = UUID.nameUUIDFromBytes(id.toByteArray()).toString(),
            locales = language.toString(),
            timeZone = TimeZone.getDefault().id.toString(),
            alphaCode = diaCode,
            isLowRamDevice = memoryInfo.lowMemory,
            physicalRamSize = (memoryInfo.totalMem / 1048576L).toInt(),
            availableRamSize = (memoryInfo.availMem / 1048576L).toInt()
        )

        return appConfig
    }

    fun generateDeviceFingerprint(): String {
        val deviceInfo = "${Build.MODEL}_${Build.BRAND}_${Build.VERSION.SDK_INT}_${BuildConfig.APPLICATION_ID}"
        return deviceInfo.hashCode().toString()
    }
}