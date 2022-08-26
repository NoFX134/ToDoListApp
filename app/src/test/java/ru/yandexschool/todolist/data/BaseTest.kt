package ru.yandexschool.todolist.data

import org.mockito.kotlin.mock
import ru.yandexschool.todolist.data.local.ToDoItemLocalDataSource
import ru.yandexschool.todolist.data.remote.ToDoItemRemoteDataSource

internal open class BaseTest {

    val remoteDataSource = mock<ToDoItemRemoteDataSource>()
    val localDataSource = mock<ToDoItemLocalDataSource>()

}

