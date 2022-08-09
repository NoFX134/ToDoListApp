package ru.yandexschool.todolist.presentation.adapter

import androidx.recyclerview.widget.DiffUtil
import ru.yandexschool.todolist.data.model.ToDoItem

class DiffCallback : DiffUtil.ItemCallback<ToDoItem>() {

    override fun areItemsTheSame(oldItem: ToDoItem, newItem: ToDoItem): Boolean {
        return oldItem.id == newItem.id

    }

    override fun areContentsTheSame(oldItem: ToDoItem, newItem: ToDoItem): Boolean {
        return oldItem == newItem
    }
}