package ru.yandexschool.todolist.presentation.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.yandexschool.todolist.data.ToDoItemRepository

class ItemViewModelFactory(
    private val toDoItemRepository: ToDoItemRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ItemListViewModel(toDoItemRepository) as T
    }
}
