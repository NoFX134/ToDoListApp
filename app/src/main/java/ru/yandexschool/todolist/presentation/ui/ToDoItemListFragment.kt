package ru.yandexschool.todolist.presentation.ui

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.launch
import ru.yandexschool.todolist.R
import ru.yandexschool.todolist.databinding.FragmentToDoItemListBinding
import ru.yandexschool.todolist.presentation.adapter.ToDoItemListAdapter
import ru.yandexschool.todolist.presentation.utils.ToDoItemState

class ToDoItemListFragment :
    BaseFragment<FragmentToDoItemListBinding>(FragmentToDoItemListBinding::inflate) {

    private lateinit var vm: ItemListViewModel


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val toDoAdapter = ToDoItemListAdapter()
        vm = (activity as MainActivity).vm
        binding.rvToDoRecyclerView.apply {
            adapter = toDoAdapter
            layoutManager = LinearLayoutManager(activity)
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                vm.toDoItemListFlow.collect { toDoItemState ->
                    when (toDoItemState) {
                        is ToDoItemState.Success -> toDoAdapter.submitList(toDoItemState.toDo)
                        is ToDoItemState.Error -> showError(toDoItemState.exception)
                    }
                }
            }
        }

        // vm.toDoItemListLiveData.observe(viewLifecycleOwner) {



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
        goToAddFab()
    }

    private fun showError(exception: Throwable) {

    }

    private fun goToAddFab() {
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
    }
}