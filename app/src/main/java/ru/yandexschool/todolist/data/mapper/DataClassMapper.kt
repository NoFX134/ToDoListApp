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

    fun responseToDoIntoListToDoItem(remote: ResponseToDo): MutableList<ToDoItem> {
        return remote.list.map { listItemIntoToDoItem(it) } as MutableList<ToDoItem>
    }

    fun listToDoItemDtoIntoListToDoItem(list: List<ToDoItemDto>): List<ToDoItem> {
        return list.map { toDoItemDtoIntoToDoItem(it) }
    }

    fun responseToDoListToDoItemDto(remote: ResponseToDo): MutableList<ToDoItemDto> {
        return remote.list.map { listItemIntoToDoItemDto(it) } as MutableList<ToDoItemDto>
    }

    fun listToDoItemDtoIntoResponseToDo(list: List<ToDoItemDto>): ResponseToDo {
        return ResponseToDo(
            list = list.map { toDoItemDtoIntoTListItem(it) },
            status = application.getString(R.string.Ok)
        )
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
            status = application.getString(R.string.Ok)
        )
    }

    private fun listItemIntoToDoItem(listItem: ListItem): ToDoItem {
        return ToDoItem(
            id = listItem.id ?: UUID.randomUUID(),
            text = listItem.text.toString(),
            importance = when (listItem.importance) {
                application.getString(R.string.importance_low) -> Importance.LOW
                application.getString(R.string.importance_basic) -> Importance.BASIC
                application.getString(R.string.importance_important) -> Importance.IMPORTANT
                else -> Importance.BASIC
            },
            deadline = listItem.deadline?.let { Date(it) },
            done = listItem.done,
            createdAt = listItem.createdAt?.let { Date(it) },
            changedAt = listItem.changedAt?.let { Date(it) },
        )
    }

    fun listItemIntoToDoItemDto(listItem: ListItem): ToDoItemDto {
        return ToDoItemDto(
            id = listItem.id ?: UUID.randomUUID(),
            text = listItem.text.toString(),
            importance = when (listItem.importance) {
                application.getString(R.string.importance_low) -> ImportanceDto.LOW
                application.getString(R.string.importance_basic) -> ImportanceDto.BASIC
                application.getString(R.string.importance_important) -> ImportanceDto.IMPORTANT
                else -> ImportanceDto.BASIC
            },
            deadline = listItem.deadline?.let { date -> Date(date) },
            done = listItem.done,
            createdAt = listItem.createdAt?.let { date -> Date(date) },
            changedAt = listItem.changedAt?.let { date -> Date(date) },
        )

    }

    private fun toDoItemDtoIntoToDoItem(toDoItemDto: ToDoItemDto): ToDoItem {
        return ToDoItem(
            id = toDoItemDto.id,
            text = toDoItemDto.text.toString(),
            importance = when (toDoItemDto.importance) {
                ImportanceDto.LOW -> Importance.LOW
                ImportanceDto.BASIC -> Importance.BASIC
                ImportanceDto.IMPORTANT -> Importance.IMPORTANT
            },
            deadline = toDoItemDto.deadline,
            done = toDoItemDto.done ?: false,
            createdAt = toDoItemDto.createdAt,
            changedAt = toDoItemDto.changedAt,
        )
    }

    fun toDoItemIntoToDoItemDto(toDoItem: ToDoItem): ToDoItemDto {
        return ToDoItemDto(
            id = toDoItem.id,
            text = toDoItem.text,
            importance = when (toDoItem.importance) {
                Importance.LOW -> ImportanceDto.LOW
                Importance.BASIC -> ImportanceDto.BASIC
                Importance.IMPORTANT -> ImportanceDto.IMPORTANT
            },
            deadline = toDoItem.deadline,
            done = toDoItem.done,
            createdAt = toDoItem.createdAt,
            changedAt = toDoItem.changedAt,
        )
    }

    private fun toDoItemDtoIntoTListItem(toDoItemDto: ToDoItemDto): ListItem {
        return ListItem(
            id = toDoItemDto.id,
            text = toDoItemDto.text.toString(),
            importance = when (toDoItemDto.importance) {
                ImportanceDto.LOW -> application.getString(R.string.importance_low)
                ImportanceDto.BASIC -> application.getString(R.string.importance_basic)
                ImportanceDto.IMPORTANT -> application.getString(R.string.importance_important)
            },
            deadline = toDoItemDto.deadline?.time,
            done = toDoItemDto.done ?: false,
            createdAt = toDoItemDto.createdAt?.time,
            changedAt = toDoItemDto.changedAt?.time,
        )
    }
}