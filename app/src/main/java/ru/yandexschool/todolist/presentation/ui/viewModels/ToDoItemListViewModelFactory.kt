package ru.yandexschool.todolist.presentation.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import ru.yandexschool.todolist.data.ToDoItemRepository

/**
 * Factory for create ToDoItemListViewModelModel
 */

@Suppress("UNCHECKED_CAST")
class ToDoItemListViewModelFactory @AssistedInject constructor(
    private val toDoItemRepository: ToDoItemRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ToDoItemListViewModel(toDoItemRepository) as T
    }

    @AssistedFactory
    interface Factory {
        fun create(): ToDoItemListViewModelFactory
    }
}

