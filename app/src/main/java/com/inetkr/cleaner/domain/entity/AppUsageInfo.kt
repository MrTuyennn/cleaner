package com.inetkr.cleaner.domain.entity

import android.graphics.drawable.Drawable

data class AppUsageInfo(
    val appName: String,
    val packageName: String,
    val icon: Drawable?
)
