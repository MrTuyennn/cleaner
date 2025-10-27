package com.inetkr.cleaner.presentation.appusage

import androidx.lifecycle.viewModelScope
import arrow.core.Either
import com.inetkr.cleaner.domain.entity.AppUsageInfo
import com.inetkr.cleaner.domain.usecase.CleanCacheAppUseCase
import com.inetkr.cleaner.domain.usecase.GetAppUsageUseCase
import com.inetkr.cleaner.domain.usecase.UnInstallAppUseCase
import com.inetkr.cleaner.utils.appcomponent.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppUsageViewModel @Inject constructor(
    private val appUsageUseCase: GetAppUsageUseCase,
    private val unInstallAppUseCase: UnInstallAppUseCase,
    private val cleanCacheAppUseCase: CleanCacheAppUseCase
): BaseViewModel() {

    val _appInfo = MutableStateFlow<AppUsageState>(AppUsageState.Idle)
    val appUsage = _appInfo.asStateFlow()


    init {
        viewModelScope.launch {
          val appUsageResult =  appUsageUseCase.invoke()
            when(appUsageResult) {
                is Either.Right -> {
                    _appInfo.value = AppUsageState.Success(appUsageResult.value)
                }
                is Either.Left -> {
                    _appInfo.value = AppUsageState.Error("Failed to get app usage")
                }
            }
        }

        UninstallReceiver.onAppUninstalledListener = { packageName ->
            removeAppUsage(packageName)
        }
    }


    fun unInstallAppUsage(appUsage: AppUsageInfo) {
        viewModelScope.launch {
            val appRemoveResult = unInstallAppUseCase.invoke(appUsage)
            when(appRemoveResult) {
                is Either.Right -> {
                  println("done")
                }
                is Either.Left -> {
                    _appInfo.value = AppUsageState.Error("Failed to remove app")
                }
            }
        }
    }

    fun removeAppUsage(packageName: String) {
        val currentState = _appInfo.value
        if (currentState is AppUsageState.Success) {
            val updatedAppUsage = currentState.appUsage.filter { it.packageName != packageName }
            _appInfo.value = AppUsageState.Success(updatedAppUsage)
        }
    }

    fun cleanCacheAppUsage(appUsage: AppUsageInfo) {
        viewModelScope.launch {
            cleanCacheAppUseCase.invoke(appUsage)
        }
    }

}