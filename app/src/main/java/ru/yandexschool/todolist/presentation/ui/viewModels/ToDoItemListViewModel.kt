package ru.yandexschool.todolist.presentation.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.yandexschool.todolist.data.ToDoItemRepository
import ru.yandexschool.todolist.data.model.ToDoItem
import ru.yandexschool.todolist.di.scope.FragmentScope
import java.util.*
import javax.inject.Inject

/**
 * ViewModel for ToDoItemListFragment
 */

@FragmentScope
class ToDoItemListViewModel @Inject constructor(
    private val toDoItemRepository: ToDoItemRepository,
) :
    ViewModel() {

    private val _toDoItemListFlow: MutableStateFlow<List<ToDoItem>> =
        MutableStateFlow(emptyList())
    val toDoItemListFlow: StateFlow<List<ToDoItem>> = _toDoItemListFlow
    val countFlow = toDoItemRepository.getCount()

    init {
        fetchToDoItem()
    }

    fun fetchToDoItem() {
        viewModelScope.launch(Dispatchers.IO) {
            toDoItemRepository.fetchItem()
                .collect { toDoItem ->
                    _toDoItemListFlow.value = toDoItem
                }
        }
    }

    fun setDone(toDoItem: ToDoItem, done: Boolean) {
        val toDoItemNew = toDoItem.copy(done = done)
        viewModelScope.launch(Dispatchers.IO) {
            toDoItemRepository.refreshItem(toDoItemNew)
        }
    }

    fun updateItem() {
        viewModelScope.launch(Dispatchers.IO) {
            toDoItemRepository.updateItem()
            fetchToDoItem()
        }
    }
}

