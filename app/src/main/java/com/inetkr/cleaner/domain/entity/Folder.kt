package com.inetkr.cleaner.domain.entity

import com.inetkr.cleaner.utils.helper.formatDateMMdd
import java.io.File

data class Folder(
    val name: String,
    val path: String,
    val totalSize: Int,
    val sTime: Long,
    val lsFile: List<File>,
    val date: String = sTime.formatDateMMdd()
) {
    init {

    }
}
