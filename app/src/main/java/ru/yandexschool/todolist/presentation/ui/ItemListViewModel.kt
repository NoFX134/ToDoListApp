package ru.yandexschool.todolist.presentation.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.yandexschool.todolist.data.ToDoItemRepository
import ru.yandexschool.todolist.data.model.Importance
import ru.yandexschool.todolist.data.model.ToDoItem
import ru.yandexschool.todolist.presentation.utils.ToDoItemState
import java.util.*

class ItemListViewModel(private val toDoItemRepository: ToDoItemRepository) : ViewModel() {

    private val _toDoItemListFlow:MutableStateFlow<ToDoItemState<List<ToDoItem>>> = MutableStateFlow(ToDoItemState.Loading())
    val toDoItemListFlow: StateFlow<ToDoItemState<List<ToDoItem>>>  = _toDoItemListFlow


    init {
        viewModelScope.launch {
            _toDoItemListFlow.value = ToDoItemState.Loading()
            delay(5000)
            toDoItemRepository.fetchToDoItem()
                .collect { toDoItemList ->
                    _toDoItemListFlow.value = ToDoItemState.Success(toDoItemList)
                }
        }
    }



    fun addToDoItem(toDoItem: ToDoItem) {
        toDoItemRepository.addTodoItem(toDoItem)
    }

//    fun deleteToDoItem(toDoItem: ToDoItem) {
//        toDoItemRepository.deleteTodoItem(toDoItem)
//    }

//    fun editToDoItem(toDoItem: ToDoItem) {
//        toDoItemRepository.editTodoItem(toDoItem)
//
//    }

    fun createToDoItem(
        editFlag: Boolean,
        text: String,
        importance: Int,
        toDoItemId: String,
        toDoItemCreatedAt: Date?
    ): ToDoItem {
        if (!editFlag) {
            return ToDoItem(
                id = UUID.randomUUID().toString(),
                text = text,
                importance =
                when (importance) {
                    0 -> Importance.LOW
                    1 -> Importance.BASIC
                    2 -> Importance.IMPORTANT
                    else -> Importance.BASIC
                },
                createdAt = Date(),
            )
        } else return ToDoItem(
            id = toDoItemId,
            text = text,
            importance =
            when (importance) {
                0 -> Importance.LOW
                1 -> Importance.BASIC
                2 -> Importance.IMPORTANT
                else -> Importance.BASIC
            },
            changedAt = Date(),
            createdAt = toDoItemCreatedAt
        )
    }

}