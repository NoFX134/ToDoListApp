package ru.yandexschool.todolist.presentation.adapter

import android.annotation.SuppressLint
import android.graphics.Paint
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import ru.yandexschool.todolist.R
import ru.yandexschool.todolist.data.model.Importance
import ru.yandexschool.todolist.data.model.ToDoItem
import ru.yandexschool.todolist.databinding.ToDoCellBinding
import ru.yandexschool.todolist.utils.dateToString
import java.util.*

/**
 * ViewHolder for ToDoItemListAdapter
 */

class ToDoItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private var binding: ToDoCellBinding = ToDoCellBinding.bind(itemView)

    fun bind(toDoItem: ToDoItem, onItemClickListener: ((ToDoItem) -> Unit)?) = with(binding) {
        itemView.setOnClickListener {
            onItemClickListener?.let {
                it(toDoItem)
            }
        }
        setDeadline(toDoItem.deadline)
        setImportance(toDoItem)
        setCheckBox(toDoItem)
    }

    private fun setDeadline(deadline: Date?) {
        with(binding)
        {
            if (deadline!=null) {
                tvDeadline.text = deadline.dateToString("dd MMMM yyyy")
                tvDeadline.visibility = View.VISIBLE
            } else {
                tvDeadline.visibility = View.GONE
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setImportance(toDoItem: ToDoItem) {
        with(binding) {
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
        }
    }

    private fun setCheckBox(toDoItem: ToDoItem) {
        with(binding) {
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
}