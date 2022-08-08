package ru.yandexschool.todolist.data.model

data class PostToDo(
    val element: ListItem,
    val status: String,
    val revision: Int? = null
)
