package ru.yandexschool.todolist.data.mapper

import android.annotation.SuppressLint
import android.app.Application
import android.provider.Settings
import ru.yandexschool.todolist.R
import ru.yandexschool.todolist.data.model.*
import java.util.*
import javax.inject.Inject

/**
 * The class is designed to convert data classes
 */

class DataClassMapper @Inject constructor(private var application: Application) {

    @SuppressLint("HardwareIds")
    private val id =
        Settings.Secure.getString(application.contentResolver, Settings.Secure.ANDROID_ID)

    fun responseToDoToListItem(remote: ResponseToDo): MutableList<ToDoItem> {
        return remote.list.map { listItemToToDoItem(it) } as MutableList<ToDoItem>
    }

    fun toDoItemToPostToDo(toDoItem: ToDoItem): PostToDo {

        return PostToDo(
            element = ListItem(
                id = toDoItem.id,
                text = toDoItem.text,
                importance = when (toDoItem.importance) {
                    Importance.LOW -> application.getString(R.string.importance_low)
                    Importance.BASIC -> application.getString(R.string.importance_basic)
                    Importance.IMPORTANT -> application.getString(R.string.importance_important)
                },
                deadline = toDoItem.deadline?.time,
                done = toDoItem.done,
                createdAt = toDoItem.createdAt?.time ?: 0,
                changedAt = toDoItem.changedAt?.time,
                lastUpdatedBy = id
            ),
            status = "ok"
        )
    }

    private fun listItemToToDoItem(list: ListItem): ToDoItem {
        return ToDoItem(
            id = list.id,
            text = list.text.toString(),
            importance = when (list.importance) {
                application.getString(R.string.importance_low) -> Importance.LOW
                application.getString(R.string.importance_basic) -> Importance.BASIC
                application.getString(R.string.importance_important) -> Importance.IMPORTANT
                else -> Importance.BASIC
            },
            deadline = list.deadline?.let { Date(it) },
            done = list.done,
            createdAt = list.createdAt?.let { Date(it) },
            changedAt = list.changedAt?.let { Date(it) },
        )
    }
}