package ru.yandexschool.todolist.data

import android.content.Context
import android.content.SharedPreferences

private const val SHARED_PREFS_NAME = "shared_prefs_name"
private const val KEY_REVISION = "revision"


class SharedPref(context: Context) {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE)

    fun save(revision: String) {
        sharedPreferences.edit().putString(KEY_REVISION, revision).apply()

    }

    fun get(): String {
        return sharedPreferences.getString(KEY_REVISION, "15").toString()

    }
}