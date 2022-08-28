package ru.yandexschool.todolist.data.utils

import android.provider.Settings
import androidx.test.core.app.ApplicationProvider
import ru.yandexschool.todolist.App
import ru.yandexschool.todolist.data.model.*
import java.util.*


object TestData {

    private val app: App = ApplicationProvider.getApplicationContext()
    val deviceId = Settings.Secure.getString(app.contentResolver, Settings.Secure.ANDROID_ID)
    private const val time = 3000L

    val listToDoItemDto = listOf(
        ToDoItemDto(
            id = UUID(0, 2),
            importance = ImportanceDto.IMPORTANT,
            text = "",
            done = false,
            deadline = Date(time),
            createdAt = Date(time),
            changedAt = Date(time)
        ),
        ToDoItemDto(
            id = UUID(0, 3),
            importance = ImportanceDto.IMPORTANT,
            text = "",
            done = false,
            deadline = Date(time),
            createdAt = Date(time),
            changedAt = Date(time)
        )
    )

    val listToDoItem = listOf(
        ToDoItem(
            id = UUID(0, 2),
            importance = Importance.IMPORTANT,
            text = "",
            done = false,
            deadline = Date(time),
            createdAt = Date(time),
            changedAt = Date(time)
        ),
        ToDoItem(
            id = UUID(0, 3),
            importance = Importance.IMPORTANT,
            text = "",
            done = false,
            deadline = Date(time),
            createdAt = Date(time),
            changedAt = Date(time)
        )
    )

    val responseToDo = ResponseToDo(
        listOf(
            ListItem(
                id = UUID(0, 2),
                importance = "important",
                text = "",
                done = false,
                deadline = Date(time).time,
                createdAt = Date(time).time,
                changedAt = Date(time).time,
                lastUpdatedBy = deviceId
            ),
            ListItem(
                id = UUID(0, 3),
                importance = "important",
                text = "",
                done = false,
                deadline = Date(time).time,
                createdAt = Date(time).time,
                changedAt = Date(time).time,
                lastUpdatedBy = deviceId
            )
        ), status = "ok"
    )

    val toDoItem = ToDoItem(
        id = UUID(0, 2),
        importance = Importance.IMPORTANT,
        text = "",
        done = false,
        deadline = Date(time),
        createdAt = Date(time),
        changedAt = Date(time)
    )

    val postToDo = PostToDo(
        ListItem(
            id = UUID(0, 2),
            importance = "important",
            text = "",
            done = false,
            deadline = Date(time).time,
            createdAt = Date(time).time,
            changedAt = Date(time).time,
            lastUpdatedBy = deviceId
        ),
        status = "ok"
    )

    val toDoItemDto = ToDoItemDto(
        id = UUID(0, 2),
        importance = ImportanceDto.IMPORTANT,
        text = "",
        done = false,
        deadline = Date(time),
        createdAt = Date(time),
        changedAt = Date(time)
    )
    val listItem = ListItem(
        id = UUID(0, 2),
        importance = "important",
        text = "",
        done = false,
        deadline = Date(time).time,
        createdAt = Date(time).time,
        changedAt = Date(time).time
    )
}