package com.inetkr.cleaner.utils.helper

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun Long.formatDateMMdd(): String {
 val date = Date(this)
 val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
 val formattedDate = dateFormat.format(date)
 return formattedDate
}