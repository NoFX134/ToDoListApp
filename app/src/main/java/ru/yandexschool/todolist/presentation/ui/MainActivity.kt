package ru.yandexschool.todolist.presentation.ui

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import ru.yandexschool.todolist.R
import ru.yandexschool.todolist.data.ToDoItemRepository
import ru.yandexschool.todolist.data.mapper.ModelMapper
import ru.yandexschool.todolist.data.remote.RetrofitInstance
import ru.yandexschool.todolist.presentation.ui.viewModels.MainViewModel
import ru.yandexschool.todolist.presentation.ui.viewModels.MainViewModelFactory
import ru.yandexschool.todolist.utils.ListRevisionStorage

private const val SHARED_PREFS_NAME = "shared_prefs_name"

class MainActivity : AppCompatActivity() {

    lateinit var vm: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val modelMapper = ModelMapper(application)
        val sharedPreferences: SharedPreferences =
            application.getSharedPreferences(SHARED_PREFS_NAME, MODE_PRIVATE)
        val listRevisionStorage = ListRevisionStorage(sharedPreferences)
        val api = RetrofitInstance(listRevisionStorage).api
        val toDoItemRepository = ToDoItemRepository(modelMapper, listRevisionStorage, api)
        val viewModelProviderFactory = MainViewModelFactory(toDoItemRepository)
        vm = ViewModelProvider(this, viewModelProviderFactory)[MainViewModel::class.java]
    }
}