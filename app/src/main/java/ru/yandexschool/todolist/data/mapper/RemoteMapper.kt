package ru.yandexschool.todolist.data.mapper

import ru.yandexschool.todolist.data.model.Importance
import ru.yandexschool.todolist.data.model.ListItem
import ru.yandexschool.todolist.data.model.ResponseToDo
import ru.yandexschool.todolist.data.model.ToDoItem
import java.util.*

class RemoteMapper {

    fun responseToDoToListItem(remote: ResponseToDo): MutableList<ToDoItem> {
        return remote.list.map { listItemToToDoItem(it) } as MutableList<ToDoItem>
    }

    private fun listItemToToDoItem(list: ListItem): ToDoItem {
        return ToDoItem(
            id = list.id,
            text = list.text,
            //TODO добавить строковые ресурсы
            importance = when (list.importance) {
                "low" -> Importance.LOW
                "basic" -> Importance.BASIC
                "important" -> Importance.IMPORTANT
                else -> Importance.BASIC
            },
            deadline = list.deadline?.let { Date(it) },
            done = list.done,
            createdAt = Date(list.createdAt),
            changedAt = list.changedAt?.let { Date(it) },

        )
    }


}