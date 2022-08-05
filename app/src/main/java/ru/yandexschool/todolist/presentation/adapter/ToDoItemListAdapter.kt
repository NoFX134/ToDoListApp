package ru.yandexschool.todolist.presentation.adapter

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources.*
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.yandexschool.todolist.R
import ru.yandexschool.todolist.data.model.Importance
import ru.yandexschool.todolist.data.model.ToDoItem
import ru.yandexschool.todolist.databinding.ToDoCellBinding

class ToDoItemListAdapter :
    ListAdapter<ToDoItem, ToDoItemListAdapter.ToDoItemViewHolder>(DiffCallback()) {

    private var onItemClickListener: ((ToDoItem) -> Unit)? = null

    fun setOnItemClickListener(listener: (ToDoItem) -> Unit) {
        onItemClickListener = listener

    }

    class ToDoItemViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        private var binding: ToDoCellBinding = ToDoCellBinding.bind(itemView)

        fun bind(toDoItem: ToDoItem, onItemClickListener: ((ToDoItem) -> Unit)?) = with(binding) {
            tvToDoText.text = toDoItem.text
            itemView.setOnClickListener {
                onItemClickListener?.let {
                    it(toDoItem)
                }
            }
            when (toDoItem.importance) {
                Importance.IMPORTANT -> cbCheck.buttonDrawable = getDrawable(
                    itemView.context,
                    R.drawable.check_box_red
                )

                else -> {
                    cbCheck.buttonDrawable = getDrawable(
                        itemView.context,
                        R.drawable.check_box_gray
                    )
                }
            }
            if (toDoItem.done) {
                cbCheck.buttonDrawable = getDrawable(
                    itemView.context,
                    R.drawable.check_box_done
                )

                tvToDoText.paint.flags = Paint.STRIKE_THRU_TEXT_FLAG
                tvToDoText.setTextColor(itemView.context.getColor(R.color.gray_light))
            }
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToDoItemViewHolder {
        return ToDoItemViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.to_do_cell, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ToDoItemViewHolder, position: Int) {
        getItem(position).let { holder.bind(it,onItemClickListener) }
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