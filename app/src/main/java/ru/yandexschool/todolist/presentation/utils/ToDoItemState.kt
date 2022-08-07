package ru.yandexschool.todolist.presentation.utils

import ru.yandexschool.todolist.data.model.ToDoItem

sealed class ToDoItemState<T>(
    val data: T? = null,
    val message: String? = null
) {
    class Success<T>(data: T) : ToDoItemState<T>(data)
    class Error<T>(message: String, data: T? = null) : ToDoItemState<T>(data, message)
    class Loading<T> : ToDoItemState<T>()
}