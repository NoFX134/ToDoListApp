package ru.yandexschool.todolist.data.local

import kotlinx.coroutines.flow.Flow
import ru.yandexschool.todolist.data.mapper.DataClassMapper
import ru.yandexschool.todolist.data.model.ToDoItem
import ru.yandexschool.todolist.data.model.ToDoItemDto
import ru.yandexschool.todolist.di.scope.ApplicationScope
import java.util.*
import javax.inject.Inject

@ApplicationScope
class ToDoItemLocalDataSource @Inject constructor(
    private val toDoItemDao: ToDoItemDao,
    private val dataClassMapper: DataClassMapper,
) {

    fun fetchItemToDb(): Flow<List<ToDoItemDto>> {
        return toDoItemDao.getAll()
    }

    suspend fun addItemToDb(toDoItem: ToDoItem) {
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

    suspend fun getOldItem(updateTime: Long): MutableList<ToDoItemDto> {
        return toDoItemDao.getOldItem(updateTime)
    }

    suspend fun getNewItem(updateTime: Long): List<ToDoItemDto> {
        return toDoItemDao.getOldItem(updateTime)
    }

    suspend fun insertToDoItemList(list: List<ToDoItemDto>) {
        toDoItemDao.insertToDoItemList(list)
    }

    suspend fun deleteOldToDoItem(idList: List<UUID>) {
        toDoItemDao.deleteOldToDoItem(idList)
    }
}