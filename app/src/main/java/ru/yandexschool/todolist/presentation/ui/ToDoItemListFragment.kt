package ru.yandexschool.todolist.presentation.ui

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import ru.yandexschool.todolist.R
import ru.yandexschool.todolist.databinding.FragmentToDoItemListBinding
import ru.yandexschool.todolist.presentation.adapter.ToDoItemListAdapter

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
        vm.toDoItemListLiveData.observe(viewLifecycleOwner) { list ->
            toDoAdapter.submitList(list)

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
        goToAddFab()
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