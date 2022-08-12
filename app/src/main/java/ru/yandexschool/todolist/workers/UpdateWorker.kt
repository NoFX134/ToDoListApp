package ru.yandexschool.todolist.workers

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import ru.yandexschool.todolist.data.ToDoItemRepository

class UpdateWorker (context: Context, params: WorkerParameters, private var repository: ToDoItemRepository) :
    CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        try {
            repository.fetchToDoItem()
            return Result.success()
        } catch (e: Exception) {
            return Result.failure()
        }
    }
}
