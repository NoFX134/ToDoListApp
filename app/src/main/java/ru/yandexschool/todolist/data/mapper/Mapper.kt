package ru.yandexschool.todolist.data.mapper

import android.annotation.SuppressLint
import android.content.Context
import android.provider.Settings
import android.util.Log
import ru.yandexschool.todolist.R
import ru.yandexschool.todolist.data.model.*
import ru.yandexschool.todolist.presentation.utils.Resource
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

    fun errorMapper(errorCode: Int?): String{
       return when(errorCode){
            -1 -> context.getString(R.string.Error_noInternet)
            404 -> context.getString(R.string.error_404)
            400 -> context.getString(R.string.error_400)
            401 -> context.getString(R.string.Error_401)
            500 -> context.getString(R.string.Error_500)
           else -> context.getString(R.string.Unknown_error)
       }
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