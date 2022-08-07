package ru.yandexschool.todolist.presentation.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.launch
import ru.yandexschool.todolist.R
import ru.yandexschool.todolist.data.model.ListItem
import ru.yandexschool.todolist.databinding.FragmentToDoItemListBinding
import ru.yandexschool.todolist.presentation.adapter.ToDoItemListAdapter
import ru.yandexschool.todolist.presentation.utils.ToDoItemState

class ToDoItemListFragment :
    BaseFragment<FragmentToDoItemListBinding>(FragmentToDoItemListBinding::inflate) {

    private lateinit var vm: ItemListViewModel
    private var toDoAdapter = ToDoItemListAdapter()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vm = (activity as MainActivity).vm
        initAdapter()
        init()
        initListeners()
    }

    private fun init() {

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                vm.toDoItemListFlow.collect { toDoItemState ->
                    when (toDoItemState) {
                        is ToDoItemState.Success -> {
                            toDoAdapter.submitList(toDoItemState.data)
                        }
                        is ToDoItemState.Error -> {
                            showError(toDoItemState.message)
                        }

                        is ToDoItemState.Loading ->showLoading()
                    }
                }
            }
        }
    }

    private fun showLoading() {
        Toast.makeText(context, "Идет загрузка", Toast.LENGTH_SHORT ).show()
    }

    private fun initListeners() {
        binding.fab.setOnClickListener {
            val bundle = Bundle()
            bundle.apply {
                putSerializable("toDoItem", null)
                putSerializable("editFlag", false)
                findNavController().navigate(
                    R.id.action_toDoItemListFragment_to_toDoAddFragment,
                    bundle
                )
            }
        }

        toDoAdapter.setOnItemClickListener {
            val bundle = Bundle()
            bundle.apply {
                putSerializable("toDoItem", it)
                putSerializable("editFlag", true)
            }
            findNavController().navigate(
                R.id.action_toDoItemListFragment_to_toDoAddFragment,
                bundle
            )
        }
    }

    private fun initAdapter() {
        binding.rvToDoRecyclerView.apply {
            adapter = toDoAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

    private fun showError(message: String?) {

    }
}