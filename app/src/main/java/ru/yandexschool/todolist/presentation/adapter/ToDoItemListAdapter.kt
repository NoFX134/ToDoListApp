package ru.yandexschool.todolist.presentation.adapter

import android.annotation.SuppressLint
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.yandexschool.todolist.R
import ru.yandexschool.todolist.data.model.Importance
import ru.yandexschool.todolist.data.model.ListItem
import ru.yandexschool.todolist.data.model.ToDoItem
import ru.yandexschool.todolist.databinding.ToDoCellBinding

class ToDoItemListAdapter :
    ListAdapter<ToDoItem, ToDoItemListAdapter.ToDoItemViewHolder>(DiffCallback()) {

    private var onItemClickListener: ((ToDoItem) -> Unit)? = null

    fun setOnItemClickListener(listener: (ToDoItem) -> Unit) {
        onItemClickListener = listener
    }

    class ToDoItemViewHolder(itemView: View, ) :
        RecyclerView.ViewHolder(itemView) {

        private var binding: ToDoCellBinding = ToDoCellBinding.bind(itemView)

        @SuppressLint("SetTextI18n")
        fun bind(toDoItem: ToDoItem, onItemClickListener: ((ToDoItem) -> Unit)? ) = with(binding) {
            itemView.setOnClickListener {
                onItemClickListener?.let {
                    it(toDoItem)
                }
            }
            when (toDoItem.importance) {
                Importance.IMPORTANT -> {
                    tvToDoText.text = "‼️${toDoItem.text}"
                    cbCheck.visibility = View.INVISIBLE
                    cbCheckRed.visibility = View.VISIBLE
                }
                Importance.LOW -> {
                    tvToDoText.text = toDoItem.text
                    ivImportance.visibility = View.VISIBLE
                    cbCheck.visibility = View.VISIBLE
                    cbCheckRed.visibility = View.GONE
                }
                Importance.BASIC -> {
                    tvToDoText.text = toDoItem.text
                    cbCheck.visibility = View.VISIBLE
                    cbCheckRed.visibility = View.GONE

                }
            }

            when {
                (toDoItem.done) -> {
                    if (toDoItem.importance == Importance.IMPORTANT) {
                        cbCheck.visibility = View.INVISIBLE
                        cbCheckRed.visibility = View.VISIBLE
                        cbCheckRed.isChecked = true
                    } else {
                        cbCheck.visibility = View.VISIBLE
                        cbCheckRed.visibility = View.GONE
                        cbCheck.isChecked = true

                    }
                    tvToDoText.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
                    tvToDoText.setTextColor(itemView.context.getColor(R.color.gray_light))
                }
            }

            cbCheck.setOnCheckedChangeListener { _, b ->
                if (b) {
                    tvToDoText.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
                    tvToDoText.setTextColor(itemView.context.getColor(R.color.gray_light))
                } else {
                    tvToDoText.paintFlags = 0
                    tvToDoText.setTextColor(itemView.context.getColor(R.color.black))
                }
            }

            cbCheckRed.setOnCheckedChangeListener { _, b ->
                if (b) {
                    tvToDoText.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
                    tvToDoText.setTextColor(itemView.context.getColor(R.color.gray_light))
                } else {
                    tvToDoText.paintFlags = 0
                    tvToDoText.setTextColor(itemView.context.getColor(R.color.black))
                }
            }
        }


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

class DiffCallback : DiffUtil.ItemCallback<ToDoItem>() {

    override fun areItemsTheSame(oldItem: ToDoItem, newItem: ToDoItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ToDoItem, newItem: ToDoItem): Boolean {
        return oldItem == newItem
    }


}