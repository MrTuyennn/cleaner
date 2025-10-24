package com.inetkr.cleaner.data.repository.scanFile

import com.inetkr.cleaner.domain.entity.MediaFile

interface ScanFileRepository {
    suspend fun scanVideoFile(): List<MediaFile>
    suspend fun scanImageFile(): List<MediaFile>
}