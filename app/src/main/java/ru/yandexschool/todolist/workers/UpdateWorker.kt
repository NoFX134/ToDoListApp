package ru.yandexschool.todolist.workers

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import ru.yandexschool.todolist.data.ToDoItemRepository


class UpdateWorker @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted private val params: WorkerParameters,
    private val repository: ToDoItemRepository
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        try {
            repository.fetchToDoItem()
            return Result.success()
        } catch (e: Exception) {
            return Result.failure()
        }
    }

    @AssistedFactory
    interface Factory {

        fun create(appContext: Context, params: WorkerParameters): UpdateWorker
    }
}
