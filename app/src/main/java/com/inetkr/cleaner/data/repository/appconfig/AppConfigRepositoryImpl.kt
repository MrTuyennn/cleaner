package com.inetkr.cleaner.data.repository.appconfig

import android.app.ActivityManager
import android.content.Context
import android.os.Build
import com.inetkr.cleaner.BuildConfig
import com.inetkr.cleaner.di.qualifiers.DefaultDispatcher
import com.inetkr.cleaner.domain.entity.AppConfig
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.withContext
import java.util.Locale
import java.util.TimeZone
import java.util.UUID
import javax.inject.Inject

class AppConfigRepositoryImpl @Inject constructor(
    @DefaultDispatcher private val coroutineDispatcher: CoroutineDispatcher,
    @ApplicationContext private val context: Context
) : AppConfigRepository {

    private val _appConfig = MutableStateFlow<AppConfig>(AppConfig())

    override val appConfigInfo: StateFlow<AppConfig> = _appConfig.asStateFlow()

    override suspend fun getAppConfig() = withContext(coroutineDispatcher) {
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
        _appConfig.value = appConfig
    }

    fun generateDeviceFingerprint(): String {
        val deviceInfo = "${Build.MODEL}_${Build.BRAND}_${Build.VERSION.SDK_INT}_${BuildConfig.APPLICATION_ID}"
        return deviceInfo.hashCode().toString()
    }

}