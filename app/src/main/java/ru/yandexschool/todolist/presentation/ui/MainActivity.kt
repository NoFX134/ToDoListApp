package ru.yandexschool.todolist.presentation.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import ru.yandexschool.todolist.R
import ru.yandexschool.todolist.data.ToDoItemRepository

class MainActivity : AppCompatActivity() {

    lateinit var vm: ItemListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toDoItemRepository = ToDoItemRepository()
        val viewModelProviderFactory = ItemViewModelFactory(toDoItemRepository)
        vm = ViewModelProvider(this, viewModelProviderFactory)[ItemListViewModel::class.java]
    }
}