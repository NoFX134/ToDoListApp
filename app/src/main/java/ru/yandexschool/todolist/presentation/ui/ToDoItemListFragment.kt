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
    val bundle = Bundle()

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
            bundle.apply {
                putSerializable("toDoItem", it)
                putInt("position", toDoAdapter.itemCount)
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
            bundle.apply {
                putSerializable("toDoItem", null)
                findNavController().navigate(
                    R.id.action_toDoItemListFragment_to_toDoAddFragment,
                    bundle
                )

            }
        }
    }
}