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

        val toDoItem = args.toDoItem
        editFlag = args.editFlag
        val cal = Calendar.getInstance()
        vm = (activity as MainActivity).vm

        initSpinner()

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

        if (toDoItem != null) {
            binding.etToDo.setText(args.toDoItem?.text ?: "")
            when (toDoItem.importance) {
                Importance.LOW -> binding.spImportance.setSelection(0)
                Importance.BASIC -> binding.spImportance.setSelection(1)
                Importance.IMPORTANT -> binding.spImportance.setSelection(2)

            }
            binding.tvDeadlineDate.text = toDoItem.deadline?.dateToString("DD-MM-YYYY")
        }


        binding.tvSave.setOnClickListener {
            saveToDoItem(createToDoItem(toDoItem))
            findNavController().popBackStack()
        }

        binding.ivClose.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.tvDelete.setOnClickListener {
            if (toDoItem != null) {
                vm.deleteToDoItem(toDoItem)
            }
            findNavController().popBackStack()
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
            vm.addToDoItem(createToDoItem(toDoItem))
        } else {
            vm.editToDoItem(createToDoItem(toDoItem))
        }
    }

    private fun createToDoItem(toDoItem: ToDoItem?): ToDoItem {
        if (!editFlag) {
            return ToDoItem(
                id = UUID.randomUUID().toString(),
                text = binding.etToDo.text.toString(),
                importance =
                when (binding.spImportance.selectedItemPosition) {
                    0 -> Importance.LOW
                    1 -> Importance.BASIC
                    2 -> Importance.IMPORTANT
                    else -> Importance.BASIC
                },
                createdAt = Date(),
            )
        } else return ToDoItem(
            id = toDoItem?.id ?: UUID.randomUUID().toString(),
            text = binding.etToDo.text.toString(),
            importance =
            when (binding.spImportance.selectedItemPosition) {
                0 -> Importance.LOW
                1 -> Importance.BASIC
                2 -> Importance.IMPORTANT
                else -> Importance.BASIC
            },
            createdAt = Date(),
        )
    }


}

