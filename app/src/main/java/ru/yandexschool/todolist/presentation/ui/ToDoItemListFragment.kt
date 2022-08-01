package ru.yandexschool.todolist.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import ru.yandexschool.todolist.R
import ru.yandexschool.todolist.data.ToDoItemRepository
import ru.yandexschool.todolist.databinding.FragmentToDoItemListBinding

class ToDoItemListFragment :
    BaseFragment<FragmentToDoItemListBinding>(FragmentToDoItemListBinding::inflate) {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val toDoAdapter = ToDoItemListAdapter()
        val toDoItemRepository = ToDoItemRepository()
        binding.rvToDoRecyclerView.apply {
            adapter = toDoAdapter
            layoutManager = LinearLayoutManager(activity)
            lifecycleScope.launch {
                toDoItemRepository.toDoItemListFlow.collectLatest { data ->
                    toDoAdapter.submitList(data)
                }
            }
        }
        toDoAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("todoItem", it)
            }
            findNavController().navigate(
                R.id.action_toDoItemListFragment_to_toDoAddFragment,
                bundle
            )

        }
        goToAddFab()
    }

    private fun goToAddFab() {
        binding.fab.setOnClickListener {
            findNavController().navigate(R.id.action_toDoItemListFragment_to_toDoAddFragment)
        }
    }
}