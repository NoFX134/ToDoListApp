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
import ru.yandexschool.todolist.data.model.ToDoItem
import ru.yandexschool.todolist.di.scope.FragmentScope
import ru.yandexschool.todolist.utils.ResponseState
import javax.inject.Inject

/**
 * ViewModel for ToDoItemListFragment
 */

@FragmentScope
class ToDoItemListViewModel @Inject constructor(private val toDoItemRepository: ToDoItemRepository) :
    ViewModel() {

    private val _toDoItemListFlow: MutableStateFlow<ResponseState<List<ToDoItem>>> =
        MutableStateFlow(ResponseState.Loading())
    val toDoItemListFlow: StateFlow<ResponseState<List<ToDoItem>>> = _toDoItemListFlow

    init {
        fetchToDoItem()
    }

    fun fetchToDoItem() {
        viewModelScope.launch(Dispatchers.IO) {
            delay(1000)
            _toDoItemListFlow.value = ResponseState.Loading()
            toDoItemRepository.fetchToDoItem()
                .collect { resource ->
                    _toDoItemListFlow.value = resource
                }
        }
    }
}