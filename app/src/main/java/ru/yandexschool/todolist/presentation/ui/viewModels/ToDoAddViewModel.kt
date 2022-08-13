package ru.yandexschool.todolist.presentation.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.yandexschool.todolist.data.ToDoItemRepository
import ru.yandexschool.todolist.data.model.Importance
import ru.yandexschool.todolist.data.model.ToDoItem
import ru.yandexschool.todolist.utils.stringToDate
import java.util.*
import javax.inject.Inject

/**
 * ViewModel for ToDoItemAddFragment
 */

class ToDoAddViewModel @Inject constructor(private val toDoItemRepository: ToDoItemRepository) :
    ViewModel() {

    fun addToDoItemApi(toDoItem: ToDoItem) {
        viewModelScope.launch(Dispatchers.IO) {
            toDoItemRepository.addTodoItem(toDoItem)
        }
    }

    fun deleteToDoItem(toDoItemId: UUID) {
        viewModelScope.launch(Dispatchers.IO) {
            toDoItemRepository.deleteTodoItem(toDoItemId)

        }
    }

    fun refreshToDoItem(toDoItemId: UUID, toDoItem: ToDoItem) {
        viewModelScope.launch(Dispatchers.IO) {
            toDoItemRepository.refreshToDoItem(toDoItemId, toDoItem)
        }
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
            deadline = toDoItemDeadline.stringToDate("dd-MM-yyyy"),
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
            deadline = toDoItemDeadline.stringToDate("dd-MM-yyyy"),
            changedAt = Date(),
            createdAt = toDoItemCreatedAt
        )
    }
}