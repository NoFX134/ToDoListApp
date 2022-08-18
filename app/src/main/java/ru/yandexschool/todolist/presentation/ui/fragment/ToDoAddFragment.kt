package ru.yandexschool.todolist.presentation.ui.fragment

import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import ru.yandexschool.todolist.App
import ru.yandexschool.todolist.R
import ru.yandexschool.todolist.data.model.Importance
import ru.yandexschool.todolist.data.model.ToDoItem
import ru.yandexschool.todolist.databinding.FragmentToDoAddBinding
import ru.yandexschool.todolist.presentation.ui.viewModels.ToDoAddViewModel
import ru.yandexschool.todolist.presentation.ui.viewModels.ToDoAddViewModelFactory
import ru.yandexschool.todolist.utils.dateToString
import java.util.*
import javax.inject.Inject

/**
 * Fragment for detailed display, editing, deleting ToDoItem
 */

class ToDoAddFragment : BaseFragment<FragmentToDoAddBinding>(FragmentToDoAddBinding::inflate) {

    private val args: ToDoAddFragmentArgs by navArgs()
    private var editFlag = false
    private val vm: ToDoAddViewModel by viewModels { factory.create() }

    @Inject
    lateinit var factory: ToDoAddViewModelFactory.Factory

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as App).appComponent.toDoAddFragmentComponent().create()
            .inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val toDoItemEdit = args.toDoItem
        editFlag = args.editFlag
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
                binding.tvDeadlineDate.text = cal.time.dateToString("dd-MM-yyyy")
            }
        binding.swDatePicker.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                context?.let { it ->
                    DatePickerDialog(
                        it, dateSetListener,
                        cal.get(Calendar.YEAR),
                        cal.get(Calendar.MONTH),
                        cal.get(Calendar.DAY_OF_MONTH)
                    ).show()
                }
            } else {
                binding.tvDeadlineDate.text = ""
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
            if (toDoItemEdit.deadline != null) binding.swDatePicker.isChecked = true
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
            toDoItem.let { vm.refreshToDoItem(createToDoItem(toDoItem)) }
        }
    }

    private fun createToDoItem(toDoItemEdit: ToDoItem?): ToDoItem {
        val text = binding.etToDo.text.toString()
        val importance = binding.spImportance.selectedItemPosition
        val toDoItemId = toDoItemEdit?.id
        val toDoItemCreatedAt = toDoItemEdit?.createdAt
        val toDoItemDeadline = binding.tvDeadlineDate.text.toString()
        return vm.createToDoItem(
            editFlag,
            text,
            importance,
            toDoItemId,
            toDoItemCreatedAt,
            toDoItemDeadline
        )
    }
}