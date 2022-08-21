package ru.yandexschool.todolist.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import ru.yandexschool.todolist.data.model.ToDoItemDto
import java.util.*

@Dao
interface ToDoItemDao {
    @Query("SELECT * FROM table_todo_item Order by created_at DESC")
    fun getAll(): Flow<List<ToDoItemDto>>

    @Query("SELECT * FROM table_todo_item Where created_at<(:updateTime)")
    fun getOldItem(updateTime: Long):List<ToDoItemDto>

    @Query("SELECT * FROM table_todo_item Where created_at>=(:updateTime)")
    fun getNewItem(updateTime: Long):List<ToDoItemDto>

    @Query("SELECT * FROM table_todo_item")
    fun getAllList():List<ToDoItemDto>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertToDoItemList(todoItemList: List<ToDoItemDto>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertToDoItem(todoItemDto: ToDoItemDto)

    @Update
    suspend fun updateToDoItem(toDoItemDto: ToDoItemDto)

    @Update
    suspend fun updateToDoItemList(toDoItemDtoList: List<ToDoItemDto>)


    @Delete
    suspend fun deleteToDoItem(toDoItemDto: ToDoItemDto)

    @Query("DELETE FROM table_todo_item WHERE id NOT IN (:idList)")
    suspend fun deleteOldToDoItem(idList: List<UUID>)

}