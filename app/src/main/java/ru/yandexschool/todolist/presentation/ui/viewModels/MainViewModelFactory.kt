package ru.yandexschool.todolist.presentation.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.yandexschool.todolist.data.ToDoItemRepository

@Suppress("UNCHECKED_CAST")
class MainViewModelFactory(
    private val toDoItemRepository: ToDoItemRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(toDoItemRepository) as T
    }
}
