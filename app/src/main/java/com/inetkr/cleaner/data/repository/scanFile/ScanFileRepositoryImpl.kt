package com.inetkr.cleaner.data.repository.scanFile

import com.inetkr.cleaner.data.local.scanFile.DataSourceScanFile
import com.inetkr.cleaner.di.qualifiers.DefaultDispatcher
import com.inetkr.cleaner.domain.entity.MediaFile
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ScanFileRepositoryImpl @Inject constructor(
    @DefaultDispatcher private val coroutineDispatcher: CoroutineDispatcher,
    private val dataSourceScanFile: DataSourceScanFile
): ScanFileRepository {
    override suspend fun scanVideoFile(): List<MediaFile> = withContext(coroutineDispatcher) {
        val allVideo =  dataSourceScanFile.scanAllVideos()
        return@withContext allVideo
    }
    override suspend fun scanImageFile(): List<MediaFile> = withContext(coroutineDispatcher) {
        val allImage = dataSourceScanFile.scanAllImages()
        return@withContext allImage
    }
}