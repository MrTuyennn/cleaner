package com.inetkr.cleaner.utils.helper

fun Long.toHumanReadableFileSize(): String {
    val bytes = this.toDouble()
    return when {
        bytes >= 1 shl 30 -> "%.1f GB".format(bytes / (1 shl 30))
        bytes >= 1 shl 20 -> "%.1f MB".format(bytes / (1 shl 20))
        bytes >= 1 shl 10 -> "%.0f KB".format(bytes / (1 shl 10))
        else -> "$bytes bytes"
    }
}