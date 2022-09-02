package ru.yandexschool.todolist

import android.app.Application
import androidx.work.*
import ru.yandexschool.todolist.di.component.AppComponent
import ru.yandexschool.todolist.di.component.DaggerAppComponent
import ru.yandexschool.todolist.workers.UpdateWorker
import ru.yandexschool.todolist.workers.UpdateWorkerFactory
import java.util.concurrent.TimeUnit
import javax.inject.Inject

open class App : Application() {

    lateinit var appComponent: AppComponent
        private set

    @Inject
    lateinit var updateWorkerFactory: UpdateWorkerFactory

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.factory().create(this)
        appComponent.inject(this)
        initWorkManager()
        launchWorkManager()
    }

    private fun initWorkManager() {
        val workManagerConfig = Configuration.Builder()
            .setWorkerFactory(updateWorkerFactory)
            .build()
        WorkManager.initialize(this, workManagerConfig)
    }

    private fun launchWorkManager() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()
        val request = PeriodicWorkRequestBuilder<UpdateWorker>(8, TimeUnit.HOURS)
            .setConstraints(constraints)
            .build()
        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            "ToDoItemUpdateWork",
            ExistingPeriodicWorkPolicy.KEEP,
            request
        )
    }
}

