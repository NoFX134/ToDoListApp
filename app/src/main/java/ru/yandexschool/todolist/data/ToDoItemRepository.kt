package ru.yandexschool.todolist.data

import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.yandexschool.todolist.data.model.Importance
import ru.yandexschool.todolist.data.model.ToDoItem
import java.text.FieldPosition
import java.util.*

class ToDoItemRepository {

    var toDoItemList: MutableList<ToDoItem> = mutableListOf()

    init {
        toDoItemList.add(
            ToDoItem(
                id = "1",
                text = "Длинный текст, длинный текст, длинный текст, длинный текст," +
                        " длинный текст, длинный текст, длинный текст, длинный текст, длинный текст",
                importance = Importance.LOW,
            )

        )
        toDoItemList.add(
            ToDoItem(
                id = "2",
                text = "Купить продукты",
                importance = Importance.IMPORTANT,
            )
        )
        toDoItemList.add(
            ToDoItem(
                id = "3",
                text = "Помыть машину",
                importance = Importance.BASIC,
                done = true
            )
        )
        toDoItemList.add(
            ToDoItem(
                id = "4",
                text = "Сделать домашнее задание до дедлайна",
                importance = Importance.BASIC,
            )
        )
        toDoItemList.add(
            ToDoItem(
                id = "5",
                text = "Сходить в магазин",
                importance = Importance.BASIC,
            )
        )
        toDoItemList.add(
            ToDoItem(
                id = "6",
                text = "Полить цветы",
                importance = Importance.BASIC,
            )
        )

        toDoItemList.add(
            ToDoItem(
                id = "7",
                text = "Покормить кошку",
                importance = Importance.BASIC,
            )
        )
        toDoItemList.add(
            ToDoItem(
                id = "8",
                text = "Посмотреть еще раз лекцию по GIT",
                importance = Importance.BASIC,
            )
        )
    }

    fun addTodoItem(toDoItem: ToDoItem) {
        toDoItemList.add(toDoItem)
    }

    fun editTodoItem(toDoItem: ToDoItem, position: Int) {
        toDoItemList[position - 1] = toDoItem
    }
}