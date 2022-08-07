package ru.yandexschool.todolist.presentation.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.yandexschool.todolist.data.ToDoItemRepository
import ru.yandexschool.todolist.data.model.Importance
import ru.yandexschool.todolist.data.model.ToDoItem
import java.util.*

class ItemListViewModel(private val toDoItemRepository: ToDoItemRepository) : ViewModel() {

    private val _toDoItemListLiveData: MutableLiveData<List<ToDoItem>> = MutableLiveData()
    val toDoItemListLiveData: LiveData<List<ToDoItem>> = _toDoItemListLiveData


    init {
        _toDoItemListLiveData.postValue(toDoItemRepository.fetchToDoItem())
    }

    fun addToDoItem(toDoItem: ToDoItem) {
        toDoItemRepository.addTodoItem(toDoItem)
        _toDoItemListLiveData.postValue(toDoItemRepository.fetchToDoItem())
    }

    fun deleteToDoItem(toDoItem: ToDoItem) {
        toDoItemRepository.deleteTodoItem(toDoItem)
    }

    fun editToDoItem(toDoItem: ToDoItem) {
        toDoItemRepository.editTodoItem(toDoItem)

    }

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