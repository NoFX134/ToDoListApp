package ru.yandexschool.todolist.presentation.utils

sealed class Resource<T>(
    val data: T? = null,
    val message: Int? = null
) {
    class Success<T>(data: T) : Resource<T>(data)
    class Error<T>(message: Int, data: T? = null) : Resource<T>(data, message)
    class Loading<T> : Resource<T>()
}