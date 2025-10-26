package com.inetkr.cleaner.data.repository.scanFile

import arrow.core.Either
import com.inetkr.cleaner.domain.entity.Folder
import com.inetkr.cleaner.domain.entity.MediaFile

interface ScanFileRepository {
    suspend fun scanVideoFile(): List<MediaFile>
    suspend fun scanImageFile(): List<MediaFile>

    suspend fun deleteFileItem(mediaFile: MediaFile): Either<Throwable, Boolean>

    suspend fun getAllFolder(): Either<Throwable, List<Folder>>
}