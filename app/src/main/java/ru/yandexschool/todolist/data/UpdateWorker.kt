package ru.yandexschool.todolist.data

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters

class UpdateWorker(private val context: Context, params: WorkerParameters) :
    Worker(context, params) {

    override fun doWork(): Result {
        val repository = ToDoItemRepository(
            context, SharedPref(context)
        )
        try {
            repository.fetchToDoItem()
            return Result.success()
        } catch (e: Exception) {
            return Result.failure()
        }

    }
}
