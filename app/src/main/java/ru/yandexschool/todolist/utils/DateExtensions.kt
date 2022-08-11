package ru.yandexschool.todolist.utils

import java.text.SimpleDateFormat
import java.util.*

fun Date.dateToString(format: String): String {
    val dateFormatter = SimpleDateFormat(format, Locale("ru"))
    return dateFormatter.format(this)
}

fun String.stringToDate(format: String): Date? {
    if (this.isNotEmpty()) {
        val dateFormatter = SimpleDateFormat(format, Locale("ru"))
        return dateFormatter.parse(this)
    }
    return null
}