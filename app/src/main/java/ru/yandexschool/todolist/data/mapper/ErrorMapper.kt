package ru.yandexschool.todolist.data.mapper

import android.content.Context
import ru.yandexschool.todolist.R

/**
 * The class is designed to convert errorCode to String
 */

class ErrorMapper(private val context: Context) {

    fun errorMapper(errorCode: Int): String {
        return when (errorCode) {
            -1 -> context.getString(R.string.Error_noInternet)
            404 -> context.getString(R.string.error_404)
            400 -> context.getString(R.string.error_400)
            401 -> context.getString(R.string.Error_401)
            500 -> context.getString(R.string.Error_500)
            else -> context.getString(R.string.Unknown_error)
        }
    }
}