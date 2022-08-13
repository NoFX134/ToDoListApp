package ru.yandexschool.todolist.data

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.yandexschool.todolist.data.mapper.DataClassMapper
import ru.yandexschool.todolist.data.model.ToDoItem
import ru.yandexschool.todolist.data.remote.Api
import ru.yandexschool.todolist.utils.ListRevisionStorage
import ru.yandexschool.todolist.utils.ResponseState
import java.util.*
import javax.inject.Inject

/**
 * A class for processing a data operation. To abstract the data layer from the rest of the application
 */

class ToDoItemRepository @Inject constructor(
    private val dataClassMapper: DataClassMapper,
    private val listRevisionStorage: ListRevisionStorage,
    private val api: Api
) {

    suspend fun fetchToDoItem(): Flow<ResponseState<List<ToDoItem>>> {
        return flow {
            try {
                val response = api.fetchToDoItemList()
                if (response.isSuccessful) {
                    response.body()?.let { resultResponse ->
                        listRevisionStorage.save(resultResponse.revision.toString())
                        emit(ResponseState.Success(dataClassMapper.responseToDoToListItem(resultResponse)))
                    }
                } else emit(ResponseState.Error(response.code()))
            } catch (e: Exception) {
                emit(ResponseState.Error(-1))
            }
        }
    }

    suspend fun addTodoItem(toDoItem: ToDoItem) {
        try {
            val response =
               api.addToDoItem(
                    dataClassMapper.toDoItemToPostToDo(
                        toDoItem
                    )
                )
            if (response.isSuccessful) {
                listRevisionStorage.save(response.body()?.revision.toString())
            } else {
                response.code()
            }
        } catch (e: Exception) {
        }
    }

    suspend fun deleteTodoItem(toDoItemId: UUID) {
        try {
            val response =
                api.deleteToDoItem(toDoItemId.toString())
            if (response.isSuccessful) {
                listRevisionStorage.save(response.body()?.revision.toString())
            } else {
                response.code()
            }
        } catch (e: Exception) {
        }
    }

    suspend fun refreshToDoItem(toDoItemId: UUID, toDoItem: ToDoItem) {
        try {
            val response = api.refreshToDoItem(
                toDoItemId.toString(),
                dataClassMapper.toDoItemToPostToDo(toDoItem)
            )
            if (response.isSuccessful) {
                listRevisionStorage.save(response.body()?.revision.toString())
            } else {
                response.code()
            }
        } catch (e: Exception) {
        }
    }
}