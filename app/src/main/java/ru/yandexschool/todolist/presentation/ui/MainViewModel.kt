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
import ru.yandexschool.todolist.presentation.utils.Resource
import ru.yandexschool.todolist.presentation.utils.stringToDate
import java.util.*

class MainViewModel(private val toDoItemRepository: ToDoItemRepository) : ViewModel() {

    private val _toDoItemListFlow: MutableStateFlow<Resource<List<ToDoItem>>> =
        MutableStateFlow(Resource.Loading())
    val toDoItemListFlow: StateFlow<Resource<List<ToDoItem>>> = _toDoItemListFlow


    init {
        fetchToDoItem()
    }

    fun addToDoItemApi(toDoItem: ToDoItem) {
        viewModelScope.launch {
            toDoItemRepository.addTodoItem(toDoItem)

        }
        fetchToDoItem()
    }

    fun deleteToDoItem(toDoItemId: UUID) {
        viewModelScope.launch {
            toDoItemRepository.deleteTodoItem(toDoItemId)

        }
        fetchToDoItem()
    }

    fun refreshToDoItem(toDoItemId: UUID, toDoItem: ToDoItem) {
        viewModelScope.launch {
            toDoItemRepository.refreshToDoItem(toDoItemId, toDoItem)
        }
        fetchToDoItem()
    }


    fun createToDoItem(
        editFlag: Boolean,
        text: String,
        importance: Int,
        toDoItemId: UUID?,
        toDoItemCreatedAt: Date?,
        toDoItemDeadline: String
    ): ToDoItem {
        if (!editFlag) return ToDoItem(
            id = UUID.randomUUID(),
            text = text,
            importance =
            when (importance) {
                0 -> Importance.LOW
                1 -> Importance.BASIC
                2 -> Importance.IMPORTANT
                else -> Importance.BASIC
            },
            deadline= toDoItemDeadline.stringToDate("dd-MM-yyyy"),
            createdAt = Date(),
            changedAt = Date()
        ) else return ToDoItem(
            id = toDoItemId,
            text = text,
            importance =
            when (importance) {
                0 -> Importance.LOW
                1 -> Importance.BASIC
                2 -> Importance.IMPORTANT
                else -> Importance.BASIC
            },
            deadline= toDoItemDeadline.stringToDate("dd-MM-yyyy"),
            changedAt = Date(),
            createdAt = toDoItemCreatedAt
        )
    }

    fun fetchToDoItem() {
        viewModelScope.launch {
            delay(1000)
            _toDoItemListFlow.value = Resource.Loading()
            toDoItemRepository.fetchToDoItem()
                .collect { resource ->
                    _toDoItemListFlow.value = resource
                }
        }
    }
}