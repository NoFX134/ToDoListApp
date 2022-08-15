package ru.yandexschool.todolist

import android.app.Application
import androidx.work.*
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import ru.yandexschool.todolist.di.component.DaggerAppComponent
import ru.yandexschool.todolist.workers.UpdateWorker
import ru.yandexschool.todolist.workers.UpdateWorkerFactory
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class App : Application(), HasAndroidInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>

    @Inject
    lateinit var updateWorkerFactory: UpdateWorkerFactory

    override fun onCreate() {
        super.onCreate()
        DaggerAppComponent.factory().create(this).inject(this)
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

    override fun androidInjector(): AndroidInjector<Any> {
        return dispatchingAndroidInjector
    }
}