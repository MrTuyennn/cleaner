package com.inetkr.cleaner.data.repository.scanFile

import arrow.core.Either
import com.inetkr.cleaner.data.local.scanFile.DataSourceScanFile
import com.inetkr.cleaner.di.qualifiers.DefaultDispatcher
import com.inetkr.cleaner.domain.entity.Folder
import com.inetkr.cleaner.domain.entity.MediaFile
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ScanFileRepositoryImpl @Inject constructor(
    @DefaultDispatcher private val coroutineDispatcher: CoroutineDispatcher,
    private val dataSourceScanFile: DataSourceScanFile,

): ScanFileRepository {

    val _folderSystem = MutableStateFlow<List<Folder>>(emptyList())
    val _currentFolderSystem = MutableStateFlow<Folder>(Folder(
        name = "",
        path = "",
        totalSize = 0,
        sTime = 0,
        lsFile = emptyList(),
        date = ""
    ))


    override val folderSystem: StateFlow<List<Folder>> = _folderSystem.asStateFlow()
    override val currentItemFolderSystem: StateFlow<Folder> = _currentFolderSystem.asStateFlow()

    override fun saveItemFolderSystem(folder: Folder) {
        _currentFolderSystem.value = folder
    }

    override suspend fun scanVideoFile(): List<MediaFile> = withContext(coroutineDispatcher) {
        val allVideo =  dataSourceScanFile.scanAllVideos()
        return@withContext allVideo
    }
    override suspend fun scanImageFile(): List<MediaFile> = withContext(coroutineDispatcher) {
        val allImage = dataSourceScanFile.scanAllImages()
        return@withContext allImage
    }

    override suspend fun deleteFileItem(mediaFile: MediaFile): Either<Throwable, Boolean> = withContext(coroutineDispatcher) {
        dataSourceScanFile.deleteItem(mediaFile)
    }

    override suspend fun getAllFolder(): Either<Throwable, List<Folder>> = withContext(coroutineDispatcher) {
        return@withContext dataSourceScanFile.getAllFolders()
    }

    override suspend fun getFolderSystem(): Either<Throwable, List<Folder>> {
        return withContext(coroutineDispatcher) {
            val resultFolderSystem = dataSourceScanFile.getFoldersFromFileSystem()
            resultFolderSystem.fold(
                ifLeft = { error ->
                    Either.Left(error)
                },
                ifRight = { result ->
                    _folderSystem.value = result
                    Either.Right(result)
                }
            )
        }
    }

}