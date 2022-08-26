package ru.yandexschool.todolist.data.remote

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.flow.Flow
import ru.yandexschool.todolist.data.mapper.DataClassMapper
import ru.yandexschool.todolist.data.model.ListItem
import ru.yandexschool.todolist.data.model.ToDoItem
import ru.yandexschool.todolist.data.model.ToDoItemDto
import ru.yandexschool.todolist.di.scope.ApplicationScope
import ru.yandexschool.todolist.utils.ListRevisionStorage
import ru.yandexschool.todolist.utils.UpdateTimeStorage
import java.net.UnknownHostException
import java.util.*
import javax.inject.Inject

@ApplicationScope
class ToDoItemRemoteDataSource @Inject constructor(
    private val api: Api,
    private val dataClassMapper: DataClassMapper,
    private val updateTimeStorage: UpdateTimeStorage,
    private val listRevisionStorage: ListRevisionStorage
) {

    private val _error: MutableLiveData<Int> = MutableLiveData()
    val error: LiveData<Int> = _error

    suspend fun fetchItemToApi(): List<ListItem> {
        try {
            val response = api.fetchToDoItemList()
            if (response.isSuccessful) {
                val updateTime = Date()
                updateTimeStorage.save(updateTime.time)
                listRevisionStorage.save(response.body()?.revision.toString())
                return response.body()?.list ?: emptyList()
            } else {
                _error.postValue(response.code())
                return emptyList()
            }
        } catch (e: Exception) {
            _error.postValue(-1)
            return emptyList()
        }
    }

    suspend fun addTodoItemApi(toDoItem: ToDoItem) {
        try {
            val response = api.addToDoItem(dataClassMapper.toDoItemToPostToDo(toDoItem))
            if (response.isSuccessful) {
                listRevisionStorage.save(response.body()?.revision.toString())
            } else {
                _error.postValue(response.code())

            }
        } catch (e: UnknownHostException) {
            e.localizedMessage?.let { Log.d("REPO", it) }
            _error.postValue(-1)
        }
    }

    suspend fun refreshToDoItemApi(toDoItem: ToDoItem) {
        try {
            val response = api.refreshToDoItem(
                toDoItem.id.toString(),
                dataClassMapper.toDoItemToPostToDo(toDoItem)
            )
            if (response.isSuccessful) {
                listRevisionStorage.save(response.body()?.revision.toString())
            } else {
                _error.postValue(response.code())
            }
        } catch (e: UnknownHostException) {
            _error.postValue(-1)
        }
    }

    suspend fun deleteTodoItemApi(toDoItemId: UUID) {
        try {
            val response = api.deleteToDoItem(toDoItemId.toString())
            if (response.isSuccessful) {
                listRevisionStorage.save(response.body()?.revision.toString())
            } else {
                _error.postValue(response.code())
            }
        } catch (e: Exception) {
            _error.postValue(-1)
        }
    }

    suspend fun updateTodoItemApi(list: List<ToDoItemDto>) {
        try {
            val response = api.updateToDoItem(dataClassMapper.listToDoItemDtoIntoResponseToDo(list))
            if (response.isSuccessful) {
                listRevisionStorage.save(response.body()?.revision.toString())
            } else {
                _error.postValue(response.code())
            }
        } catch (e: Exception) {
            _error.postValue(-1)
        }
    }
}