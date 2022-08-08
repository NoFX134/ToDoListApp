package ru.yandexschool.todolist.presentation.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import ru.yandexschool.todolist.R
import ru.yandexschool.todolist.data.SharedPref
import ru.yandexschool.todolist.data.ToDoItemRepository

class MainActivity : AppCompatActivity() {

    lateinit var vm: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val sharedPref = SharedPref(applicationContext)
        val toDoItemRepository = ToDoItemRepository(application, sharedPref)
        val viewModelProviderFactory = MainViewModelFactory(toDoItemRepository)
        vm = ViewModelProvider(this, viewModelProviderFactory)[MainViewModel::class.java]


    }
}