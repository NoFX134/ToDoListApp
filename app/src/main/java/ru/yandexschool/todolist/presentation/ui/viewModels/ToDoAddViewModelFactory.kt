package ru.yandexschool.todolist.presentation.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.yandexschool.todolist.data.ToDoItemRepository
import javax.inject.Inject

/**
 * Factory for create ToDoItemListViewModelModel
 */

@Suppress("UNCHECKED_CAST")
class ToDoAddViewModelFactory  @Inject constructor(private val toDoItemRepository: ToDoItemRepository
    ) : ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return ToDoAddViewModel(toDoItemRepository) as T
        }
    }