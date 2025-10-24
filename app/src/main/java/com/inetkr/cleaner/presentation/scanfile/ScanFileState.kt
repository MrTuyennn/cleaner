package com.inetkr.cleaner.presentation.scanfile

import com.inetkr.cleaner.domain.entity.MediaFile

sealed class ScanFileState {
    object Idle : ScanFileState()

    object Loading : ScanFileState()

    data class Success(
        val images: List<MediaFile>,
        val videos: List<MediaFile>
    ) : ScanFileState()

    data class Error(val message: String) : ScanFileState()
}