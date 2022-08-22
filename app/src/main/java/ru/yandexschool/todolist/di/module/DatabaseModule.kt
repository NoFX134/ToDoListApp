package ru.yandexschool.todolist.di.module

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import ru.yandexschool.todolist.data.local.ToDoItemDao
import ru.yandexschool.todolist.data.local.ToDoItemDatabase
import ru.yandexschool.todolist.di.scope.ApplicationScope

@Module
class DatabaseModule {

    companion object {

        private const val DATABASE_NAME = "todo_item_database"
    }

    @ApplicationScope
    @Provides
    fun provideToDoItemDataBase(application: Application): ToDoItemDao{
        return Room.databaseBuilder(application, ToDoItemDatabase::class.java, DATABASE_NAME)
            .build()
            .toDoItemDao()
    }
}