package ru.yandexschool.todolist.presentation.ui.viewModels

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import ru.yandexschool.todolist.data.ToDoItemRepository
import ru.yandexschool.todolist.data.model.Importance
import ru.yandexschool.todolist.data.model.ToDoItem
import ru.yandexschool.todolist.di.scope.FragmentScope
import ru.yandexschool.todolist.utils.stringToDate
import java.util.*
import javax.inject.Inject

/**
 * ViewModel for ToDoItemAddFragment
 */

@FragmentScope
class ToDoAddViewModel @Inject constructor(
    private val toDoItemRepository: ToDoItemRepository,
) :
    ViewModel() {
    private val apiScope = CoroutineScope(Dispatchers.IO)

    fun addToDoItemApi(toDoItem: ToDoItem) {
       apiScope.launch {
            toDoItemRepository.addItem(toDoItem)
        }
    }

    fun deleteToDoItem(toDoItem: ToDoItem) {
        apiScope.launch {
            toDoItemRepository.deleteItem(toDoItem)

        }
    }

    fun refreshToDoItem(toDoItem: ToDoItem) {
       apiScope.launch {
            toDoItemRepository.refreshItem(toDoItem)
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
            id = toDoItemId ?: UUID.randomUUID(),
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