package ru.yandexschool.todolist.data

import android.content.Context
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.yandexschool.todolist.data.mapper.Mapper
import ru.yandexschool.todolist.data.model.ToDoItem
import ru.yandexschool.todolist.data.remote.RetrofitInstance

class ToDoItemRepository(context: Context) {

    private val mapper = Mapper(context)

    fun fetchToDoItem(): Flow<List<ToDoItem>> {
        return flow {
            val response = RetrofitInstance.api.fetchToDoItemList()
            emit(mapper.responseToDoToListItem(response))
        }
    }

    suspend fun addTodoItem(toDoItem: ToDoItem) {
        RetrofitInstance.api.addToDoItemList(mapper.toDoItemToPostToDo(toDoItem))
    }



}