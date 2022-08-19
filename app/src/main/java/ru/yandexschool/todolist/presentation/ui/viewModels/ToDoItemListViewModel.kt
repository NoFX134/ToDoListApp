package ru.yandexschool.todolist.presentation.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.yandexschool.todolist.data.ToDoItemRepository
import ru.yandexschool.todolist.data.ToDoItemRepositoryTest
import ru.yandexschool.todolist.data.model.ToDoItem
import ru.yandexschool.todolist.di.scope.FragmentScope
import ru.yandexschool.todolist.utils.ResponseState
import java.util.*
import javax.inject.Inject

/**
 * ViewModel for ToDoItemListFragment
 */

@FragmentScope
class ToDoItemListViewModel @Inject constructor(
    private val toDoItemRepository: ToDoItemRepository,
    private val toDoItemRepositoryTest: ToDoItemRepositoryTest
) :
    ViewModel() {

    private val _toDoItemListFlow: MutableStateFlow<List<ToDoItem>> =
        MutableStateFlow(emptyList())
    val toDoItemListFlow: StateFlow<List<ToDoItem>> = _toDoItemListFlow

    init {
        fetchToDoItem()
    }

    fun fetchToDoItem() {
        viewModelScope.launch(Dispatchers.IO) {
            toDoItemRepositoryTest.fetchItem()
                .collect { toDoItem ->
                    _toDoItemListFlow.value = toDoItem
                }
        }
    }

    fun setDone(toDoItem: ToDoItem, done: Boolean) {
        val toDoItemNew = toDoItem.copy(done = done)
        viewModelScope.launch(Dispatchers.IO) {
            toDoItem.id.let { toDoItemRepository.refreshToDoItem(it, toDoItemNew) }
        }
    }

    fun updateItem(){
        viewModelScope.launch(Dispatchers.IO) {
            toDoItemRepositoryTest.updateItem()
        }
    }
}
