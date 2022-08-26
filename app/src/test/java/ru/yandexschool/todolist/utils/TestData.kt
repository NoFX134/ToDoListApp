package ru.yandexschool.todolist.utils

import kotlinx.coroutines.flow.flow
import ru.yandexschool.todolist.data.model.*
import java.util.*


internal object TestData {


    val toDoItemTestOne = ToDoItem(
        id = UUID(1, 1),
        text = "test",
        importance = Importance.IMPORTANT,
        deadline = Date(22225),
        done = true,
        changedAt = Date(22224),
        createdAt = Date(22224),
    )

    val toDoItemTestTwo = ToDoItem(
        id = UUID(1, 1),
        text = "test",
        importance = Importance.IMPORTANT,
        deadline = Date(22225),
        done = true,
        changedAt = Date(22224),
        createdAt = Date(22224),
    )

    val toDoItemTestList = listOf(
        toDoItemTestOne, toDoItemTestTwo
    )

    val toDoItemDtoTest = ToDoItemDto(
        id = UUID(1, 1),
        text = "test",
        importance = ImportanceDto.IMPORTANT,
        deadline = Date(22225),
        done = true,
        changedAt = Date(22224),
        createdAt = Date(22224),
    )

    val toDoItemDtoTestList = listOf(
        toDoItemDtoTest
    )

    val getCountTestFlow = flow {
        emit(1)
    }

    val fetchItemTestFlow = flow {
        emit(toDoItemTestList)
    }

    val listOldItemTest = mutableListOf<ToDoItemDto>(
        ToDoItemDto(
            id = UUID(0, 0),
            importance = ImportanceDto.IMPORTANT,
            text = "",
            done = false,
            deadline = null,
            createdAt = null,
            changedAt = null
        ),
        ToDoItemDto(
            id = UUID(0, 1),
            importance = ImportanceDto.IMPORTANT,
            text = "",
            done = false,
            deadline = null,
            createdAt = null,
            changedAt = null
        )
    )
    val listNewItemTest = listOf(
        ToDoItemDto(
            id = UUID(0, 2),
            importance = ImportanceDto.IMPORTANT,
            text = "",
            done = false,
            deadline = null,
            createdAt = null,
            changedAt = null
        ),
        ToDoItemDto(
            id = UUID(0, 3),
            importance = ImportanceDto.IMPORTANT,
            text = "",
            done = false,
            deadline = null,
            createdAt = null,
            changedAt = null
        )
    )

    val listToDoItemApiTest = listOf(
        ToDoItemDto(
            id = UUID(0, 0),
            importance = ImportanceDto.IMPORTANT,
            text = "",
            done = false,
            deadline = null,
            createdAt = null,
            changedAt = null
        ),
        ToDoItemDto(
            id = UUID(0, 4),
            importance = ImportanceDto.IMPORTANT,
            text = "",
            done = false,
            deadline = null,
            createdAt = null,
            changedAt = null
        )
    )
    val mergedList = listOf(
        ToDoItemDto(
            id = UUID(0, 0),
            importance = ImportanceDto.IMPORTANT,
            text = "",
            done = false,
            deadline = null,
            createdAt = null,
            changedAt = null
        ), ToDoItemDto(
            id = UUID(0, 2),
            importance = ImportanceDto.IMPORTANT,
            text = "",
            done = false,
            deadline = null,
            createdAt = null,
            changedAt = null
        ),
        ToDoItemDto(
            id = UUID(0, 3),
            importance = ImportanceDto.IMPORTANT,
            text = "",
            done = false,
            deadline = null,
            createdAt = null,
            changedAt = null
        ),
        ToDoItemDto(
            id = UUID(0, 4),
            importance = ImportanceDto.IMPORTANT,
            text = "",
            done = false,
            deadline = null,
            createdAt = null,
            changedAt = null
        )
    )
}