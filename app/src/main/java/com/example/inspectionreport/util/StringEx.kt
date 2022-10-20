package com.example.inspectionreport.util

import java.text.SimpleDateFormat
import java.util.*

fun getToday(): String {
    val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale("pt", "BR"))
    val today = Calendar.getInstance().time
    return dateFormat.format(today)
}

fun formatDate(date: String): String {
    return date.replace("-", "/")
}