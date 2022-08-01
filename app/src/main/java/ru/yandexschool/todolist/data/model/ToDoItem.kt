package ru.yandexschool.todolist.data.model

import java.io.Serializable
import java.time.LocalDate
import java.util.*

data class ToDoItem(
    var id: String,
    val text: String,
    val importance: Importance = Importance.BASIC,
    val deadline: Date = Date(1),
    val done: Boolean = false,
    val createdAt: Date = Date(1),
    val changedAt: Date = Date(1)
): Serializable



enum class Importance {
    LOW,
    BASIC,
    IMPORTANT
}