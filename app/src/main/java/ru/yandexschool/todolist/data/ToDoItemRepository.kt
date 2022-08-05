package ru.yandexschool.todolist.data

import android.util.Log
import ru.yandexschool.todolist.data.model.Importance
import ru.yandexschool.todolist.data.model.ToDoItem
import java.util.*

class ToDoItemRepository {

    private val toDoItemList: MutableList<ToDoItem> = mutableListOf()

    init {
        toDoItemList.add(
            ToDoItem(
                id = UUID.randomUUID().toString(),
                text = "Длинный текст, длинный текст, длинный текст, длинный текст," +
                        " длинный текст, длинный текст, длинный текст, длинный текст, длинный текст",
                importance = Importance.LOW,
            )
        )
        toDoItemList.add(
            ToDoItem(
                id = UUID.randomUUID().toString(),
                text = "Длинный текст, длинный текст, длинный текст, длинный текст," +
                        " длинный текст, длинный текст, длинный текст, длинный текст, длинный текст",
                importance = Importance.BASIC,
                done = true
            )
        )
        toDoItemList.add(
            ToDoItem(
                id = UUID.randomUUID().toString(),
                text = "Купить продукты",
                importance = Importance.IMPORTANT,
            )
        )
        toDoItemList.add(
            ToDoItem(
                id = UUID.randomUUID().toString(),
                text = "Помыть машину",
                importance = Importance.BASIC,
                done = true
            )
        )
        toDoItemList.add(
            ToDoItem(
                id = UUID.randomUUID().toString(),
                text = "Сделать домашнее задание до дедлайна",
                importance = Importance.BASIC,
            )
        )
        toDoItemList.add(
            ToDoItem(
                id = UUID.randomUUID().toString(),
                text = "Сходить в магазин",
                done = true,
                importance = Importance.BASIC,
            )
        )
        toDoItemList.add(
            ToDoItem(
                id = UUID.randomUUID().toString(),
                text = "Полить цветы",
                importance = Importance.BASIC,
            )
        )

        toDoItemList.add(
            ToDoItem(
                id = UUID.randomUUID().toString(),
                text = "Покормить кошку",
                importance = Importance.LOW,

                )
        )
        toDoItemList.add(
            ToDoItem(
                id = UUID.randomUUID().toString(),
                text = "Исправить ошибки",
                importance = Importance.LOW,
                done = true
            )
        )
        toDoItemList.add(
            ToDoItem(
                id = UUID.randomUUID().toString(),
                text = "Что-то нужно сделать",
                importance = Importance.IMPORTANT,
                done = true
            )
        )
        toDoItemList.add(
            ToDoItem(
                id = UUID.randomUUID().toString(),
                text = "Важное дело с деадлайном",
                importance = Importance.IMPORTANT,
                deadline = Date(),


            )
        )
        toDoItemList.add(
            ToDoItem(
                id = UUID.randomUUID().toString(),
                text = "Простое дело с деадлайном",
                importance = Importance.BASIC,
                deadline = Date(),


            )
        )
        toDoItemList.add(
            ToDoItem(
                id = UUID.randomUUID().toString(),
                text = "Выполненое дело с деадлайном",
                importance = Importance.BASIC,
                deadline = Date(),
                done = true
            )
        )
    }

    fun fetchToDoItem(): List<ToDoItem> {
        return toDoItemList
    }

    fun addTodoItem(toDoItem: ToDoItem) {
        toDoItemList.add(0, toDoItem)
    }

    fun editTodoItem(toDoItem: ToDoItem) {
        toDoItemList.replaceAll {

            when {
                (it.id == toDoItem.id) -> ToDoItem(
                    it.id,
                    toDoItem.text,
                    toDoItem.importance,
                    toDoItem.deadline,
                    toDoItem.done,
                    it.createdAt,
                    toDoItem.createdAt
                )

                else -> {
                    it
                }
            }
        }
    }

    fun deleteTodoItem(toDoItem: ToDoItem) {
        toDoItemList.remove(toDoItem)
    }


}