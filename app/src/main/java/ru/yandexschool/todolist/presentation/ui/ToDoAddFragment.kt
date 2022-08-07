package ru.yandexschool.todolist.presentation.ui

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import ru.yandexschool.todolist.R
import ru.yandexschool.todolist.data.model.Importance
import ru.yandexschool.todolist.data.model.ToDoItem
import ru.yandexschool.todolist.databinding.FragmentToDoAddBinding
import ru.yandexschool.todolist.presentation.utils.dateToString
import java.text.SimpleDateFormat
import java.util.*

class ToDoAddFragment : BaseFragment<FragmentToDoAddBinding>(FragmentToDoAddBinding::inflate) {

    private lateinit var vm: ItemListViewModel
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
                val myFormat = "dd-mm-yyyy"
                val sdf = SimpleDateFormat(myFormat, Locale.US)
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
                vm.deleteToDoItem(toDoItemEdit)
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
            binding.tvDeadlineDate.text = toDoItemEdit.deadline?.dateToString("dd-mm-yyyy")
        }
    }

    private fun initSpinner() {
        context?.let {
            ArrayAdapter.createFromResource(
                it,
                R.array.importance_array,
                android.R.layout.simple_spinner_item
            ).also { adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                binding.spImportance.adapter = adapter
                binding.spImportance.setSelection(1)
            }
        }
    }

    private fun saveToDoItem(toDoItem: ToDoItem) {
        if (!editFlag) {
            vm.addToDoItem(toDoItem)
        } else {
            vm.editToDoItem(createToDoItem(toDoItem))
        }
    }

    private fun createToDoItem(toDoItemEdit: ToDoItem?): ToDoItem {
        val text = binding.etToDo.text.toString()
        val importance = binding.spImportance.selectedItemPosition
        val toDoItemId = toDoItemEdit?.id.toString()
        val toDoItemCreatedAt = toDoItemEdit?.createdAt
        return vm.createToDoItem(editFlag, text, importance, toDoItemId, toDoItemCreatedAt)
    }
}

