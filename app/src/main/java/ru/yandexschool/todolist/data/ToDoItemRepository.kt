package ru.yandexschool.todolist.data

import android.content.Context
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.yandexschool.todolist.data.mapper.Mapper
import ru.yandexschool.todolist.data.model.ToDoItem
import ru.yandexschool.todolist.data.remote.RetrofitInstance
import ru.yandexschool.todolist.presentation.utils.Resource
import java.util.*

class ToDoItemRepository( context: Context, private val sharedPref: SharedPref) {

    private val mapper = Mapper(context)


    fun fetchToDoItem(): Flow<Resource<List<ToDoItem>>> {
        return flow {
            try {
                val response = RetrofitInstance(sharedPref).api.fetchToDoItemList()
                if (response.isSuccessful) {
                    response.body()?.let { resultResponse ->
                        sharedPref.save(resultResponse.revision.toString())
                        emit(Resource.Success(mapper.responseToDoToListItem(resultResponse)))
                    }
                } else emit(Resource.Error(response.code()))
            } catch (e: Exception) {
                emit(Resource.Error(-1))
            }
        }
    }

    suspend fun addTodoItem(toDoItem: ToDoItem) {
        try {
            val response =
                RetrofitInstance(sharedPref).api.addToDoItem(mapper.toDoItemToPostToDo(toDoItem))
            if (response.isSuccessful) {
                sharedPref.save(response.body()?.revision.toString())
            } else {
                response.code()
            }
        } catch (e: Exception) {
        }
    }


    suspend fun deleteTodoItem(toDoItemId: UUID) {
        try {
            val response = RetrofitInstance(sharedPref).api.deleteToDoItem(toDoItemId.toString())
            if (response.isSuccessful) {
                sharedPref.save(response.body()?.revision.toString())
            } else {
                response.code()
            }
        } catch (e: Exception) {
        }
    }

    suspend fun refreshToDoItem(toDoItemId: UUID, toDoItem: ToDoItem) {
        try {
            val response = RetrofitInstance(sharedPref).api.refreshToDoItem(
                toDoItemId.toString(),
                mapper.toDoItemToPostToDo(toDoItem)
            )
            if (response.isSuccessful) {
                sharedPref.save(response.body()?.revision.toString())
            } else {
                response.code()
            }
        } catch (e: Exception) {
        }
    }
}








