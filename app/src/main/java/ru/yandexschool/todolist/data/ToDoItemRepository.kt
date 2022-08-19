package ru.yandexschool.todolist.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.yandexschool.todolist.data.local.ToDoItemDao
import ru.yandexschool.todolist.data.mapper.DataClassMapper
import ru.yandexschool.todolist.data.model.ListItem
import ru.yandexschool.todolist.data.model.ToDoItem
import ru.yandexschool.todolist.data.model.ToDoItemDto
import ru.yandexschool.todolist.data.remote.Api
import ru.yandexschool.todolist.di.scope.ApplicationScope
import ru.yandexschool.todolist.utils.ListRevisionStorage
import java.net.UnknownHostException
import java.util.*
import javax.inject.Inject

/**
 * A class for processing a data operation. To abstract the data layer from the rest of the application
 */
@ApplicationScope
class ToDoItemRepository @Inject constructor(
    private val dataClassMapper: DataClassMapper,
    private val listRevisionStorage: ListRevisionStorage,
    private val api: Api,
    private val toDoItemDao: ToDoItemDao
) {

    private val _error: MutableLiveData<Int> = MutableLiveData()
    val error: LiveData<Int> = _error

    suspend fun fetchItem(): Flow<List<ToDoItem>> {
        val todoItemApi = fetchItemToApi().map { dataClassMapper.listItemIntoToDoItemDto(it) }
        val idList = todoItemApi.map { it.id }
        if (fetchItemToApi().isNotEmpty()) {
            toDoItemDao.insertToDoItemList(todoItemApi)
            toDoItemDao.deleteOldToDoItem(idList)
        }
        return fetchItemToDb().map { dataClassMapper.listToDoItemDtoIntoListToDoItem(it) }
    }

    suspend fun addItem(toDoItem: ToDoItem) {
        addItemToDb(toDoItem)
        addTodoItemApi(toDoItem)
    }

    suspend fun refreshItem(toDoItem: ToDoItem) {
        refreshItemToDb(toDoItem)
        refreshToDoItemApi(toDoItem)
    }

    suspend fun deleteItem(toDoItem: ToDoItem) {
        deleteItemToDb(toDoItem)
        deleteTodoItemApi(toDoItem.id)
    }

    suspend fun updateItem(){
        val listToDoItemDto = toDoItemDao.getAllList()
        val listTodoItemApi = fetchItemToApi().map { dataClassMapper.listItemIntoToDoItemDto(it) }
        val mergedList = listTodoItemApi as MutableList<ToDoItemDto>
        listToDoItemDto.forEach {toDoItemDto ->
            if (!listTodoItemApi.contains(toDoItemDto)) mergedList.add(toDoItemDto)
            updateTodoItemApi(mergedList)
        }
    }

    private suspend fun fetchItemToApi(): List<ListItem> {
        try {
            val response = api.fetchToDoItemList()
            if (response.isSuccessful) {
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

    private suspend fun addTodoItemApi(toDoItem: ToDoItem) {
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

    private suspend fun refreshToDoItemApi(toDoItem: ToDoItem) {
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

    private suspend fun deleteTodoItemApi(toDoItemId: UUID) {
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

    private suspend fun updateTodoItemApi(list: List<ToDoItemDto>) {
        try {
            val response =  api.updateToDoItem(dataClassMapper.listToDoItemDtoIntoResponseToDo(list))
            if (response.isSuccessful) {
                listRevisionStorage.save(response.body()?.revision.toString())
            } else {
                _error.postValue(response.code())
            }
        } catch (e: Exception) {
            _error.postValue(-1)
        }
    }

    private fun fetchItemToDb(): Flow<List<ToDoItemDto>> {
        return toDoItemDao.getAll()
    }

    private suspend fun addItemToDb(toDoItem: ToDoItem) {
        toDoItemDao.insertToDoItem(dataClassMapper.toDoItemIntoToDoItemDto(toDoItem))
    }

    private suspend fun refreshItemToDb(toDoItem: ToDoItem) {
        toDoItemDao.updateToDoItem(dataClassMapper.toDoItemIntoToDoItemDto(toDoItem))
    }

    private suspend fun deleteItemToDb(toDoItem: ToDoItem) {
        toDoItemDao.deleteToDoItem(dataClassMapper.toDoItemIntoToDoItemDto(toDoItem))
    }
}