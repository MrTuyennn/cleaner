package com.inetkr.cleaner.presentation.appusage

import com.inetkr.cleaner.domain.entity.AppUsageInfo
import com.inetkr.cleaner.domain.entity.Folder
import com.inetkr.cleaner.domain.entity.MediaFile

sealed class AppUsageState {
    object Idle : AppUsageState()

    object Loading : AppUsageState()

    data class Success(
        val appUsage: List<AppUsageInfo>,
    ) : AppUsageState()

    data class Error(val message: String) : AppUsageState()

    val isLoading: Boolean
        get() = this is Loading || this is Idle
}