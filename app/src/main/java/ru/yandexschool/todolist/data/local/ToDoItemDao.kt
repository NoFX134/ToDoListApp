package ru.yandexschool.todolist.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import ru.yandexschool.todolist.data.model.ToDoItemDto

@Dao
interface ToDoItemDao {
    @Query("SELECT * FROM table_todo_item")
    fun getAll(): Flow<ToDoItemDto>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertToDoItemList(todoItemList: List<ToDoItemDto>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertToDoItem(todoItemList: ToDoItemDto)

    @Update
    suspend fun updateToDoItem(toDoItemDto: ToDoItemDto)

    @Delete
    suspend fun deleteToDoItem(toDoItemDto: ToDoItemDto)

}