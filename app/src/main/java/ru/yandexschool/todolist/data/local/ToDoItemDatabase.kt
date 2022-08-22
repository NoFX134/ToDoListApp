package ru.yandexschool.todolist.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ru.yandexschool.todolist.data.local.typeConverter.DateTypeConverter
import ru.yandexschool.todolist.data.model.ToDoItemDto

@Database(
    entities =
    [ToDoItemDto::class],
    version = 1,
)
@TypeConverters(DateTypeConverter::class)
abstract class ToDoItemDatabase : RoomDatabase() {

    abstract fun toDoItemDao(): ToDoItemDao
}