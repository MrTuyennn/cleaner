package com.inetkr.cleaner.domain.entity

import android.net.Uri
import com.inetkr.cleaner.utils.helper.toHumanReadableFileSize

enum class MediaType {
    VIDEO, IMAGE
}

data class MediaFile(
    val id: Long,
    val uri: Uri,
    val name: String,
    val duration: Int? = null,
    val size: Long,
    val type: MediaType,
    val thumbnailUri: String,
    val readableSize: String = size.toHumanReadableFileSize()
)