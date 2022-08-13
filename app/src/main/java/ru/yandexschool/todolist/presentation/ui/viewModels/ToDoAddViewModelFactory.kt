package ru.yandexschool.todolist.presentation.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import ru.yandexschool.todolist.data.ToDoItemRepository
import ru.yandexschool.todolist.di.scope.FragmentScope

/**
 * Factory for create ToDoItemListViewModelModel
 */

@Suppress("UNCHECKED_CAST")
class ToDoAddViewModelFactory @AssistedInject constructor(
    private val toDoItemRepository: ToDoItemRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ToDoAddViewModel(toDoItemRepository) as T
    }

    @FragmentScope
    @AssistedFactory
    interface Factory {

        fun create(): ToDoAddViewModelFactory
    }
}
