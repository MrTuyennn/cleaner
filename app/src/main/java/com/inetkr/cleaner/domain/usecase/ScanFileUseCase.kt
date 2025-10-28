package com.inetkr.cleaner.domain.usecase

import arrow.core.Either
import com.inetkr.cleaner.data.repository.scanFile.ScanFileRepository
import com.inetkr.cleaner.domain.entity.Folder
import com.inetkr.cleaner.domain.entity.MediaFile
import javax.inject.Inject

class ScanFileVideoUseCase @Inject constructor(
    private val scanFileRepository: ScanFileRepository
) {
    suspend operator fun invoke(): Either<Throwable, List<MediaFile>> {
        return try {
            val videos = scanFileRepository.scanVideoFile()
            Either.Right(videos)
        } catch (e: Exception) {
            Either.Left(e)
        }
    }
}

class ScanFileImageUseCase @Inject constructor(
    private val scanFileRepository: ScanFileRepository
) {
    suspend operator fun invoke(): Either<Throwable, List<MediaFile>> {
        return try {
            val images = scanFileRepository.scanImageFile()
            Either.Right(images)
        } catch (e: Exception) {
            Either.Left(e)
        }
    }
}

class DeleteFileItemUseCase @Inject constructor(
    private val scanFileRepository: ScanFileRepository
) {
    suspend operator fun invoke(mediaFile: MediaFile): Either<Throwable, Boolean> {
        return scanFileRepository.deleteFileItem(mediaFile)
    }
}

class GetAllFolderUseCase @Inject constructor(
    private val scanFileRepository: ScanFileRepository
) {
    suspend operator fun invoke(): Either<Throwable, List<Folder>> {
      return scanFileRepository.getAllFolder()
    }
}

class GetFolderSystemUseCase @Inject constructor(
    private val scanFileRepository: ScanFileRepository
) {
    suspend operator fun invoke(): Either<Throwable, List<Folder>> {
        return scanFileRepository.getFolderSystem()
    }
}