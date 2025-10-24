package com.inetkr.cleaner.domain.entity

import android.net.Uri

enum class MediaType {
    VIDEO, IMAGE
}

data class MediaFile(
    val uri: Uri,
    val name: String,
    val duration: Int? = null,
    val size: Int,
    val type: MediaType,
    val thumbnailUri: String
)