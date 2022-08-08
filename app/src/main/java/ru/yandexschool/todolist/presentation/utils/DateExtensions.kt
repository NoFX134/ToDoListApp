package ru.yandexschool.todolist.presentation.utils

import java.text.SimpleDateFormat
import java.util.*

fun Date.dateToString(format: String): String {

    val dateFormatter = SimpleDateFormat(format, Locale("ru"))
    return dateFormatter.format(this)

}