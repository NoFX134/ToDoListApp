package ru.yandexschool.todolist.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import ru.yandexschool.todolist.R
import ru.yandexschool.todolist.data.model.ToDoItem



class ToDoItemListAdapter :
    ListAdapter<ToDoItem, ToDoItemViewHolder>(DiffCallback()) {

    private var onItemClickListener: ((ToDoItem) -> Unit)? = null

    fun setOnItemClickListener(listener: (ToDoItem) -> Unit) {
        onItemClickListener = listener
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToDoItemViewHolder {
        return ToDoItemViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.to_do_cell, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ToDoItemViewHolder, position: Int) {
        getItem(position).let { holder.bind(it, onItemClickListener) }
    }
}

