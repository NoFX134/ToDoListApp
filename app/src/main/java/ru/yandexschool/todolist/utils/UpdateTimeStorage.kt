package ru.yandexschool.todolist.utils

import android.content.SharedPreferences
import javax.inject.Inject

private const val KEY_UPDATE_TIME = "updateTime"

/**
 * Class to save and retrieve the latest revision
 */

class UpdateTimeStorage @Inject constructor(private val sharedPreferences: SharedPreferences) {

    fun save(updateTime: Long) {
        sharedPreferences.edit().putLong(KEY_UPDATE_TIME, updateTime).apply()
    }

    fun get(): Long {
        return sharedPreferences.getLong(KEY_UPDATE_TIME, 0)
    }
}