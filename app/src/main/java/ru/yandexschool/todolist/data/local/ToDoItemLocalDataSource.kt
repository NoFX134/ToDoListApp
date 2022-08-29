package ru.yandexschool.todolist.data.local

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.yandexschool.todolist.data.mapper.DataClassMapper
import ru.yandexschool.todolist.data.model.ToDoItem
import ru.yandexschool.todolist.data.model.ToDoItemDto
import ru.yandexschool.todolist.di.scope.ApplicationScope
import ru.yandexschool.todolist.utils.UpdateTimeStorage
import java.util.*
import javax.inject.Inject

@ApplicationScope
class ToDoItemLocalDataSource @Inject constructor(
    private val toDoItemDao: ToDoItemDao,
    private val updateTimeStorage: UpdateTimeStorage,
    private val dataClassMapper: DataClassMapper
) {

    fun fetchItemToDb(): Flow<List<ToDoItem>> {
        return toDoItemDao.getAll().map { dataClassMapper.listToDoItemDtoIntoListToDoItem(it)  }
    }

    suspend fun addToDoItemToDb(toDoItem: ToDoItem) {
        toDoItemDao.insertToDoItem(dataClassMapper.toDoItemIntoToDoItemDto(toDoItem))
    }

    suspend fun refreshItemToDb(toDoItem: ToDoItem) {
        toDoItemDao.updateToDoItem(dataClassMapper.toDoItemIntoToDoItemDto(toDoItem))
    }

    suspend fun deleteItemToDb(toDoItem: ToDoItem) {
        toDoItemDao.deleteToDoItem(dataClassMapper.toDoItemIntoToDoItemDto(toDoItem))
    }

    fun getCount(): Flow<Int> {
        return toDoItemDao.getCount()
    }

    suspend fun getOldItem(): MutableList<ToDoItemDto> {
        return toDoItemDao.getOldItem(updateTimeStorage.get())
    }

    suspend fun getNewItem(): List<ToDoItemDto> {
        return toDoItemDao.getOldItem(updateTimeStorage.get())
    }

    suspend fun insertToDoItemList(list: List<ToDoItemDto>) {
        toDoItemDao.insertToDoItemList(list)
    }

    suspend fun deleteOldToDoItem(idList: List<UUID>) {
        toDoItemDao.deleteOldToDoItem(idList)
    }
}