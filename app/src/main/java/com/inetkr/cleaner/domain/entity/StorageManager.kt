package com.inetkr.cleaner.domain.entity

import com.inetkr.cleaner.utils.helper.toHumanReadableFileSize

data class StorageManagers(
    val totalBytes: Long,
    val usedBytes: Long,
    val freeBytes: Long,

    val readableTotal: String = totalBytes.toHumanReadableFileSize(),
    val readableUsed: String = usedBytes.toHumanReadableFileSize(),
    val readableFree: String = freeBytes.toHumanReadableFileSize(),
)
