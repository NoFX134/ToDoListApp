package ru.yandexschool.todolist.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.yandexschool.todolist.data.mapper.ModelMapper
import ru.yandexschool.todolist.data.model.ToDoItem
import ru.yandexschool.todolist.data.remote.Api
import ru.yandexschool.todolist.data.remote.RetrofitInstance
import ru.yandexschool.todolist.utils.ListRevisionStorage
import ru.yandexschool.todolist.utils.Resource
import java.util.*

class ToDoItemRepository(
    private val modelMapper: ModelMapper,
    private val listRevisionStorage: ListRevisionStorage,
    private val api: Api
) {

    fun fetchToDoItem(): Flow<Resource<List<ToDoItem>>> {
        return flow {
            try {
                val response = api.fetchToDoItemList()
                if (response.isSuccessful) {
                    response.body()?.let { resultResponse ->
                        listRevisionStorage.save(resultResponse.revision.toString())
                        emit(Resource.Success(modelMapper.responseToDoToListItem(resultResponse)))
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
                RetrofitInstance(listRevisionStorage).api.addToDoItem(
                    modelMapper.toDoItemToPostToDo(
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
                modelMapper.toDoItemToPostToDo(toDoItem)
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