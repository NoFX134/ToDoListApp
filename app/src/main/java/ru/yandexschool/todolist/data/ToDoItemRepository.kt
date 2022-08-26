package ru.yandexschool.todolist.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.yandexschool.todolist.data.local.ToDoItemLocalDataSource
import ru.yandexschool.todolist.data.mapper.DataClassMapper
import ru.yandexschool.todolist.data.model.ToDoItem
import ru.yandexschool.todolist.data.model.ToDoItemDto
import ru.yandexschool.todolist.data.remote.ToDoItemRemoteDataSource
import ru.yandexschool.todolist.di.scope.ApplicationScope
import ru.yandexschool.todolist.utils.UpdateTimeStorage
import javax.inject.Inject

/**
 * A class for processing a data operation. To abstract the data layer from the rest of the application
 */
@ApplicationScope
class ToDoItemRepository @Inject constructor(
    private val dataClassMapper: DataClassMapper,
    private val updateTimeStorage: UpdateTimeStorage,
    private val toDoItemLocalDataSource: ToDoItemLocalDataSource,
    private val toDoItemRemoteDataSource: ToDoItemRemoteDataSource
) {

    val error = toDoItemRemoteDataSource.error

    suspend fun fetchItem(): Flow<List<ToDoItem>> {
        val todoItemApi = toDoItemRemoteDataSource.fetchItemToApi()
            .map { dataClassMapper.listItemIntoToDoItemDto(it) }
        val idList = todoItemApi.map { it.id }
        if (todoItemApi.isNotEmpty()) {
            toDoItemLocalDataSource.insertToDoItemList(todoItemApi)
            toDoItemLocalDataSource.deleteOldToDoItem(idList)
        }
        return toDoItemLocalDataSource.fetchItemToDb()
            .map { dataClassMapper.listToDoItemDtoIntoListToDoItem(it) }
    }

    suspend fun addItem(toDoItem: ToDoItem) {
        toDoItemLocalDataSource.addItemToDb(toDoItem)
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

        var listOldItem = toDoItemLocalDataSource.getOldItem(updateTimeStorage.get())
        val listNewItem = toDoItemLocalDataSource.getNewItem(updateTimeStorage.get())
        val listTodoItemApi = toDoItemRemoteDataSource.fetchItemToApi()
            .map { dataClassMapper.listItemIntoToDoItemDto(it) }
        listOldItem = deleteOldItem(listOldItem, listTodoItemApi)
        val listToDoItemDto = listOldItem.plus(listNewItem)
        val mergedList = listTodoItemApi as MutableList<ToDoItemDto>
        listToDoItemDto.forEach { toDoItemDto ->
            if (!listTodoItemApi.contains(toDoItemDto)) mergedList.add(toDoItemDto)
        }
        toDoItemRemoteDataSource.updateTodoItemApi(mergedList)
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