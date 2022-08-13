package ru.yandexschool.todolist.data.mapper

import android.app.Application
import android.content.Context
import ru.yandexschool.todolist.R
import javax.inject.Inject

/**
 * The class is designed to convert errorCode to String
 */

class ErrorMapper @Inject constructor(private val application: Application) {

    fun errorMapper(errorCode: Int): String {
        return when (errorCode) {
            -1 -> application.getString(R.string.Error_noInternet)
            404 -> application.getString(R.string.error_404)
            400 -> application.getString(R.string.error_400)
            401 -> application.getString(R.string.Error_401)
            500 -> application.getString(R.string.Error_500)
            else -> application.getString(R.string.Unknown_error)
        }
    }
}