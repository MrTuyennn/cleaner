package com.inetkr.cleaner.domain.entity

import android.graphics.drawable.Drawable
import com.inetkr.cleaner.utils.helper.toHumanReadableFileSize

data class AppUsageInfo(
    val appName: String,
    val packageName: String,
    val icon: Drawable?,
    val size: Long,
    val readableSize: String = size.toHumanReadableFileSize()
)
