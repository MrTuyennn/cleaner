package com.inetkr.cleaner.data.repository.memorymanager

import arrow.core.Either
import com.inetkr.cleaner.domain.entity.MemoryManager
import com.inetkr.cleaner.domain.entity.StorageManagers

interface MemoryManagerRepository {
    suspend fun getMemoryInfo(): Either<Throwable, MemoryManager>

    suspend fun getStorageManager(): Either<Throwable, StorageManagers>
}