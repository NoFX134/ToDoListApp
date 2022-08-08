package ru.yandexschool.todolist.presentation.ui

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import ru.yandexschool.todolist.R
import ru.yandexschool.todolist.data.mapper.Mapper
import ru.yandexschool.todolist.data.model.*
import ru.yandexschool.todolist.databinding.FragmentToDoAddBinding
import ru.yandexschool.todolist.presentation.utils.dateToString
import java.text.SimpleDateFormat
import java.util.*

class ToDoAddFragment : BaseFragment<FragmentToDoAddBinding>(FragmentToDoAddBinding::inflate) {

    private lateinit var vm: MainViewModel
    private val args: ToDoAddFragmentArgs by navArgs()
    private var editFlag = false


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val toDoItemEdit = args.toDoItem
        editFlag = args.editFlag
        vm = (activity as MainActivity).vm
        initSpinner()
        initToDo(toDoItemEdit)
        initListeners(toDoItemEdit)
        initCalendar()
    }

    private fun initCalendar() {
        val cal = Calendar.getInstance()
        val dateSetListener =
            DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                val myFormat = "dd-MM-yyyy"
                val sdf = SimpleDateFormat(myFormat, Locale("ru"))
                binding.tvDeadlineDate.text = sdf.format(cal.time)
            }
        binding.swDatePicker.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked)
                context?.let { it ->
                    DatePickerDialog(
                        it, dateSetListener,
                        cal.get(Calendar.YEAR),
                        cal.get(Calendar.MONTH),
                        cal.get(Calendar.DAY_OF_MONTH)
                    ).show()
                }
        }
    }

    private fun initListeners(toDoItemEdit: ToDoItem?) {
        binding.tvSave.setOnClickListener {
            saveToDoItem(createToDoItem(toDoItemEdit))
            findNavController().popBackStack()
        }
        binding.ivClose.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.tvDelete.setOnClickListener {
            if (toDoItemEdit != null) {
                toDoItemEdit.id?.let { id -> vm.deleteToDoItem(id) }
            }
            findNavController().popBackStack()
        }
    }

    private fun initToDo(toDoItemEdit: ToDoItem?) {
        if (toDoItemEdit != null) {
            binding.etToDo.setText(args.toDoItem?.text ?: "")
            when (toDoItemEdit.importance) {
                Importance.LOW -> binding.spImportance.setSelection(0)
                Importance.BASIC -> binding.spImportance.setSelection(1)
                Importance.IMPORTANT -> binding.spImportance.setSelection(2)
            }
            binding.tvDeadlineDate.text = toDoItemEdit.deadline?.dateToString("dd-MM-yyyy")
        }
    }

    private fun initSpinner() {
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.importance_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spImportance.adapter = adapter
            binding.spImportance.setSelection(1)
        }
    }

    private fun saveToDoItem(toDoItem: ToDoItem) {

        if (!editFlag) {
             vm.addToDoItemApi(toDoItem)
        } else {
         toDoItem.id?.let { vm.refreshToDoItem(it, createToDoItem(toDoItem)) }
        }

    }


    private fun createToDoItem(toDoItemEdit: ToDoItem?): ToDoItem {
        val text = binding.etToDo.text.toString()
        val importance = binding.spImportance.selectedItemPosition
        val toDoItemId = toDoItemEdit?.id
        val toDoItemCreatedAt = toDoItemEdit?.createdAt
        return vm.createToDoItem(editFlag, text, importance, toDoItemId, toDoItemCreatedAt)
    }
}

