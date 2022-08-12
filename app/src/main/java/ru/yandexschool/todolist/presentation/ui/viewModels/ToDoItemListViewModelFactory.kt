package ru.yandexschool.todolist.presentation.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.yandexschool.todolist.data.ToDoItemRepository

/**
 * Factory for create ToDoItemListViewModel
 */

@Suppress("UNCHECKED_CAST")
class ToDoItemListViewModelFactory(
    private val toDoItemRepository: ToDoItemRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ToDoItemListViewModel(toDoItemRepository) as T
    }
}