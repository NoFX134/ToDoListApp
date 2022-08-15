package ru.yandexschool.todolist.presentation.ui

import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import ru.yandexschool.todolist.R
import ru.yandexschool.todolist.data.ToDoItemRepository
import ru.yandexschool.todolist.data.mapper.DataClassMapper
import ru.yandexschool.todolist.data.remote.RetrofitInstance
import ru.yandexschool.todolist.presentation.ui.viewModels.ToDoAddViewModel
import ru.yandexschool.todolist.presentation.ui.viewModels.ToDoAddViewModelFactory
import ru.yandexschool.todolist.presentation.ui.viewModels.ToDoItemListViewModel
import ru.yandexschool.todolist.presentation.ui.viewModels.ToDoItemListViewModelFactory
import ru.yandexschool.todolist.utils.ListRevisionStorage

private const val SHARED_PREFS_NAME = "shared_prefs_name"

class MainActivity : AppCompatActivity() {

    lateinit var vmList: ToDoItemListViewModel
    lateinit var vmAdd: ToDoAddViewModel

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val dataClassMapper = DataClassMapper(application)
        val sharedPreferences: SharedPreferences =
            application.getSharedPreferences(SHARED_PREFS_NAME, MODE_PRIVATE)
        val listRevisionStorage = ListRevisionStorage(sharedPreferences)
        val api = RetrofitInstance(listRevisionStorage).api
        val toDoItemRepository = ToDoItemRepository(dataClassMapper, listRevisionStorage, api)
        val viewModelProviderFactoryVmList = ToDoItemListViewModelFactory(toDoItemRepository)
        val viewModelProviderFactoryVmAdd = ToDoAddViewModelFactory(toDoItemRepository)
        vmList = ViewModelProvider(this, viewModelProviderFactoryVmList)[ToDoItemListViewModel::class.java]
        vmAdd = ViewModelProvider(this, viewModelProviderFactoryVmAdd)[ToDoAddViewModel::class.java]
    }
}