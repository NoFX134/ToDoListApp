package ru.yandexschool.todolist

import android.app.Application
import android.content.Context
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import ru.yandexschool.todolist.di.component.AppComponent
import ru.yandexschool.todolist.di.component.DaggerAppComponent
import ru.yandexschool.todolist.workers.UpdateWorker
import java.util.concurrent.TimeUnit

class App : Application() {

    lateinit var appComponent: AppComponent
    private set

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.factory().create(this)
        val request = PeriodicWorkRequestBuilder<UpdateWorker>(8, TimeUnit.HOURS).build()
        WorkManager.getInstance(applicationContext).enqueueUniquePeriodicWork(
            "ToDoItemUpdateWork",
            ExistingPeriodicWorkPolicy.KEEP,
            request
        )
    }
}

