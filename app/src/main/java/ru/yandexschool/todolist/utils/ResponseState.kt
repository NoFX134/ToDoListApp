package ru.yandexschool.todolist.utils

/**
 * Sealed class for processing a request from the server
*/

sealed class ResponseState<T>(
    val data: T? = null,
    val message: Int = 0
) {
    class Success<T>(data: T) : ResponseState<T>(data)
    class Error<T>(message: Int, data: T? = null) : ResponseState<T>(data, message)
    class Loading<T> : ResponseState<T>()
}