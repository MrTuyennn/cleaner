package com.inetkr.cleaner.domain.usecase

import arrow.core.Either
import com.inetkr.cleaner.data.repository.memorymanager.MemoryManagerRepository
import com.inetkr.cleaner.domain.entity.MemoryManager
import com.inetkr.cleaner.domain.entity.StorageManagers
import javax.inject.Inject

class MemoryManagerUseCase @Inject constructor(
    private val memoryManagerRepository: MemoryManagerRepository
) {
    suspend operator fun invoke(): Either<Throwable, MemoryManager> = memoryManagerRepository.getMemoryInfo()
}

class StorageManagerUseCase @Inject constructor(
    private val memoryManagerRepository: MemoryManagerRepository
) {
    suspend operator fun invoke(): Either<Throwable, StorageManagers> = memoryManagerRepository.getStorageManager()
}