package ru.yandexschool.todolist.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.yandexschool.todolist.data.local.ToDoItemLocalDataSource
import ru.yandexschool.todolist.data.mapper.DataClassMapper
import ru.yandexschool.todolist.data.model.ToDoItem
import ru.yandexschool.todolist.data.model.ToDoItemDto
import ru.yandexschool.todolist.data.remote.ToDoItemRemoteDataSource
import ru.yandexschool.todolist.di.scope.ApplicationScope
import java.util.*
import javax.inject.Inject

/**
 * A class for processing a data operation. To abstract the data layer from the rest of the application
 */
@ApplicationScope
class ToDoItemRepository @Inject constructor(
    private val toDoItemLocalDataSource: ToDoItemLocalDataSource,
    private val toDoItemRemoteDataSource: ToDoItemRemoteDataSource
) {

    val error = toDoItemRemoteDataSource.error

    suspend fun fetchItem(): Flow<List<ToDoItem>> {
        val todoItemApi = toDoItemRemoteDataSource.fetchItemToApi()
        val idList = todoItemApi.map { it.id }
        if (todoItemApi.isNotEmpty()) {
            toDoItemLocalDataSource.insertToDoItemList(todoItemApi)
            toDoItemLocalDataSource.deleteOldToDoItem(idList)
        }
        return toDoItemLocalDataSource.fetchItemToDb()

    }

    suspend fun addItem(toDoItem: ToDoItem) {
        toDoItemLocalDataSource.addToDoItemToDb(toDoItem)
        toDoItemRemoteDataSource.addTodoItemApi(toDoItem)

    }

    suspend fun refreshItem(toDoItem: ToDoItem) {
        toDoItemLocalDataSource.refreshItemToDb(toDoItem)
        toDoItemRemoteDataSource.refreshToDoItemApi(toDoItem)
    }

    suspend fun deleteItem(toDoItem: ToDoItem) {
        toDoItemLocalDataSource.deleteItemToDb(toDoItem)
        toDoItemRemoteDataSource.deleteTodoItemApi(toDoItem.id)
    }

    suspend fun updateItem() {
        var listOldItem = toDoItemLocalDataSource.getOldItem()
        val listNewItem = toDoItemLocalDataSource.getNewItem()
        val listTodoItemApi = toDoItemRemoteDataSource.fetchItemToApi()
        listOldItem = deleteOldItem(listOldItem, listTodoItemApi)
        val listToDoItemDto = listOldItem.plus(listNewItem)
        val mergedList = listTodoItemApi.toMutableList()
        listToDoItemDto.forEach { toDoItemDto ->
            if (!listTodoItemApi.contains(toDoItemDto)) mergedList.add(toDoItemDto)
        }
        toDoItemRemoteDataSource.updateTodoItemApi(mergedList.sortedBy { it.createdAt })
    }

    fun getCount(): Flow<Int> {
        return toDoItemLocalDataSource.getCount()
    }

    private fun deleteOldItem(
        listOldItem: MutableList<ToDoItemDto>,
        listTodoItemApi: List<ToDoItemDto>
    ): MutableList<ToDoItemDto> {
        val iterator = listOldItem.iterator()
        while (iterator.hasNext()) {
            val item = iterator.next()
            if (!listTodoItemApi.contains(item)) {
                iterator.remove()
            }
        }
        return listOldItem
    }
}