package ru.yandexschool.todolist.presentation.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import ru.yandexschool.todolist.App
import ru.yandexschool.todolist.R
import ru.yandexschool.todolist.data.ToDoItemRepository
import ru.yandexschool.todolist.presentation.ui.viewModels.ToDoAddViewModel
import ru.yandexschool.todolist.presentation.ui.viewModels.ToDoAddViewModelFactory
import ru.yandexschool.todolist.presentation.ui.viewModels.ToDoItemListViewModel
import ru.yandexschool.todolist.presentation.ui.viewModels.ToDoItemListViewModelFactory
import javax.inject.Inject

private const val SHARED_PREFS_NAME = "shared_prefs_name"

class MainActivity : AppCompatActivity() {

    lateinit var vmList: ToDoItemListViewModel
    lateinit var vmAdd: ToDoAddViewModel
    @Inject
    lateinit var toDoItemRepository: ToDoItemRepository

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        (application as App).appComponent.inject(this)
        val viewModelProviderFactoryVmList = ToDoItemListViewModelFactory(toDoItemRepository)
        val viewModelProviderFactoryVmAdd = ToDoAddViewModelFactory(toDoItemRepository)
        vmList = ViewModelProvider(this, viewModelProviderFactoryVmList)[ToDoItemListViewModel::class.java]
        vmAdd = ViewModelProvider(this, viewModelProviderFactoryVmAdd)[ToDoAddViewModel::class.java]
    }
}