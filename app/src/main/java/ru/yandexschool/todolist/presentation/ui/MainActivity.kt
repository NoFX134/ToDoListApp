package ru.yandexschool.todolist.presentation.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import ru.yandexschool.todolist.R
import ru.yandexschool.todolist.data.SharedPref
import ru.yandexschool.todolist.data.ToDoItemRepository
import ru.yandexschool.todolist.data.UpdateWorker
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    lateinit var vm: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val sharedPref = SharedPref(applicationContext)
        val toDoItemRepository = ToDoItemRepository(application, sharedPref)
        val viewModelProviderFactory = ItemViewModelFactory(toDoItemRepository)
        vm = ViewModelProvider(this, viewModelProviderFactory)[MainViewModel::class.java]


    }
}