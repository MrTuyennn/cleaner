package com.inetkr.cleaner.data.repository.memorymanager

import arrow.core.Either
import com.inetkr.cleaner.data.local.memorymanager.DataSourceMemoryManager
import com.inetkr.cleaner.di.qualifiers.DefaultDispatcher
import com.inetkr.cleaner.domain.entity.MemoryManager
import com.inetkr.cleaner.domain.entity.StorageManagers
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MemoryManagerRepositoryImpl @Inject constructor(
    @DefaultDispatcher private val coroutineDispatcher: CoroutineDispatcher,
    private val dataSourceMemoryManager: DataSourceMemoryManager
): MemoryManagerRepository  {
    override suspend fun getMemoryInfo(): Either<Throwable, MemoryManager> = withContext(coroutineDispatcher) {
      return@withContext dataSourceMemoryManager.getMemoryInfo()
    }

    override suspend fun getStorageManager(): Either<Throwable, StorageManagers> = withContext(coroutineDispatcher) {
        return@withContext dataSourceMemoryManager.getStorageInfo()
    }
}