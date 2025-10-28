package com.inetkr.cleaner.domain.entity

import com.inetkr.cleaner.utils.helper.formatDateMMdd

data class Folder(
    val name: String,
    val path: String,
    val totalSize: Int,
    val sTime: Long,
    val date: String = sTime.formatDateMMdd()
) {
    init {

    }
}
