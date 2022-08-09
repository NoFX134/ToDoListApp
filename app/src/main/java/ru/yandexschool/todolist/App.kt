package ru.yandexschool.todolist

import android.app.Application
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import ru.yandexschool.todolist.data.UpdateWorker
import java.util.concurrent.TimeUnit

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        val request = PeriodicWorkRequestBuilder<UpdateWorker>(8, TimeUnit.HOURS).build()
        WorkManager.getInstance(applicationContext).enqueueUniquePeriodicWork(
            "ToDoItemUpdateWork",
            ExistingPeriodicWorkPolicy.KEEP,
            request
        )
    }
}
