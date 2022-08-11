package ru.yandexschool.todolist.data.model

import java.io.Serializable
import java.time.LocalDate
import java.util.*

data class ToDoItem(
    val id: UUID?,
    val text: String,
    val importance: Importance = Importance.BASIC,
    val deadline: Date? = null,
    val done: Boolean = false,
    val createdAt: Date? = Date(),
    val changedAt: Date
): Serializable

enum class Importance {
    LOW,
    BASIC,
    IMPORTANT
}