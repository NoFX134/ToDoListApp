package ru.yandexschool.todolist.data.model

/**
 * Model for POST request
 */

data class PostToDo(
    val element: ListItem,
    val status: String,
    val revision: Int? = null
)