package ru.yandexschool.todolist.utils

import android.content.SharedPreferences

private const val KEY_REVISION = "revision"

class ListRevisionStorage(private val sharedPreferences: SharedPreferences) {

    fun save(revision: String) {
        sharedPreferences.edit().putString(KEY_REVISION, revision).apply()
    }

    fun get(): String {
        return sharedPreferences.getString(KEY_REVISION, "").toString()
    }
}