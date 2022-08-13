package ru.yandexschool.todolist.workers

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import ru.yandexschool.todolist.data.ToDoItemRepository
import javax.inject.Inject


class UpdateWorker (context: Context, params: WorkerParameters) :
    CoroutineWorker(context, params) {

    @Inject lateinit var repository: ToDoItemRepository

    override suspend fun doWork(): Result {
        try {
            repository.fetchToDoItem()
            return Result.success()
        } catch (e: Exception) {
            return Result.failure()
        }
    }
}
