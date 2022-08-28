package ru.yandexschool.todolist.di.module

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.provider.Settings
import dagger.Module
import dagger.Provides
import ru.yandexschool.todolist.data.mapper.ErrorMapper
import ru.yandexschool.todolist.data.model.DeviceId
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

    @SuppressLint("HardwareIds")
    @Provides
    @ApplicationScope
    fun provideDeviceID(application: Application): DeviceId =
        DeviceId(Settings.Secure.getString(application.contentResolver, Settings.Secure.ANDROID_ID))
}