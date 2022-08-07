package ru.yandexschool.todolist.presentation.utils

import ru.yandexschool.todolist.data.model.ToDoItem

sealed class ToDoItemState {
    data class Success(val toDo: List<ToDoItem>) : ToDoItemState()
    data  class Error(val exception: Throwable) : ToDoItemState()
}
