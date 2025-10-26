package com.inetkr.cleaner.presentation.appusage

import androidx.lifecycle.viewModelScope
import arrow.core.Either
import com.inetkr.cleaner.domain.usecase.GetAppUsageUseCase
import com.inetkr.cleaner.utils.appcomponent.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppUsageViewModel @Inject constructor(
    private val appUsageUseCase: GetAppUsageUseCase
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
    }

}