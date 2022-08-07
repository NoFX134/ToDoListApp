package ru.yandexschool.todolist.data.mapper

import android.annotation.SuppressLint
import android.content.Context
import android.provider.Settings
import ru.yandexschool.todolist.R
import ru.yandexschool.todolist.data.model.*
import java.util.*

class Mapper(private var context: Context) {

    @SuppressLint("HardwareIds")
    private val id = Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
    fun responseToDoToListItem(remote: ResponseToDo): MutableList<ToDoItem> {
        return remote.list.map { listItemToToDoItem(it) } as MutableList<ToDoItem>
    }

    fun toDoItemToPostToDo(toDoItem: ToDoItem): PostToDo {
        return PostToDo(
            element = ListItem(
                id = toDoItem.id,
                text = toDoItem.text,
                importance = when (toDoItem.importance) {
                    Importance.LOW -> context.getString(R.string.importance_low)
                    Importance.BASIC -> context.getString(R.string.importance_basic)
                    Importance.IMPORTANT -> context.getString(R.string.importance_important)
                },
                deadline = toDoItem.deadline?.time,
                done = toDoItem.done,
                createdAt = toDoItem.createdAt?.time ?: 0,
                changedAt = toDoItem.changedAt.time,
                lastUpdatedBy = id
            ),
            status = "ok"
        )
    }

    private fun listItemToToDoItem(list: ListItem): ToDoItem {
        return ToDoItem(
            id = list.id,
            text = list.text,
            importance = when (list.importance) {
                context.getString(R.string.importance_low) -> Importance.LOW
                context.getString(R.string.importance_basic) -> Importance.BASIC
                context.getString(R.string.importance_important) -> Importance.IMPORTANT
                else -> Importance.BASIC
            },
            deadline = list.deadline?.let { Date(it) },
            done = list.done,
            createdAt = Date(list.createdAt),
            changedAt = Date(list.changedAt),
        )
    }
}