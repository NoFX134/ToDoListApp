package ru.yandexschool.todolist.di.module

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import ru.yandexschool.todolist.data.mapper.ErrorMapper
import ru.yandexschool.todolist.di.scope.ApplicationScope
import javax.inject.Singleton

@Module
class AppModule {

    companion object {

        const val SHARED_PREFS_NAME = "shared_prefs_name"
    }

    @Provides
    @ApplicationScope
    fun provideSharedPreferences(application: Application): SharedPreferences =
        application.getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE)
}