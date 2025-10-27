package com.inetkr.cleaner.domain.entity

import com.inetkr.cleaner.utils.helper.toHumanReadableFileSize

data class MemoryManager(
    val totalMemory: Long,
    val availableMemory: Long,
    val usedMemory: Long,

    // --- convert --- //
    val readableTotal: String = totalMemory.toHumanReadableFileSize(),
    val readableAvailable: String = availableMemory.toHumanReadableFileSize(),
    val readableUsed: String = usedMemory.toHumanReadableFileSize(),
)
