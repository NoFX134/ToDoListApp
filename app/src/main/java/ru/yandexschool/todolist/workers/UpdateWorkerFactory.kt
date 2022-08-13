package ru.yandexschool.todolist.workers

import javax.inject.Inject
import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters

class UpdateWorkerFactory @Inject constructor(
    private val helloWorldWorkerFactory: UpdateWorker.Factory,
) : WorkerFactory() {

    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters,
    ): ListenableWorker? {
        return when (workerClassName) {
            UpdateWorker::class.java.name ->
                helloWorldWorkerFactory.create(appContext, workerParameters)
            else -> null
        }
    }
}