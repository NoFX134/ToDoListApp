package ru.yandexschool.todolist.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.yandexschool.todolist.data.mapper.RemoteMapper
import ru.yandexschool.todolist.data.model.ToDoItem
import ru.yandexschool.todolist.data.remote.RetrofitInstance
import java.util.*

class ToDoItemRepository {



    fun fetchToDoItem(): Flow<List<ToDoItem>> {
        return flow {
            val response = RetrofitInstance.api.fetchToDoItemList()
            emit(RemoteMapper().responseToDoToListItem(response))
        }
    }

    fun addTodoItem(toDoItem: ToDoItem) {

    }

//    fun editTodoItem(toDoItem: ToDoItem) {
//        toDoItemList.replaceAll {
//
//            when {
//                (it.id == toDoItem.id) -> ToDoItem(
//                    it.id,
//                    toDoItem.text,
//                    toDoItem.importance,
//                    toDoItem.deadline,
//                    toDoItem.done,
//                    it.createdAt,
//                    toDoItem.createdAt
//                )
//
//                else -> {
//                    it
//                }
//            }
//        }
//    }
//
//    fun deleteTodoItem(toDoItem: ToDoItem) {
//        toDoItemList.remove(toDoItem)
//    }


}