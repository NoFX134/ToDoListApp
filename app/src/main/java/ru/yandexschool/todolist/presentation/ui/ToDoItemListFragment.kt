package ru.yandexschool.todolist.presentation.ui

import android.net.ConnectivityManager
import android.net.Network
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat.getSystemService
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import ru.yandexschool.todolist.R
import ru.yandexschool.todolist.data.mapper.Mapper
import ru.yandexschool.todolist.databinding.FragmentToDoItemListBinding
import ru.yandexschool.todolist.presentation.adapter.ToDoItemListAdapter
import ru.yandexschool.todolist.presentation.utils.Resource

class ToDoItemListFragment :
    BaseFragment<FragmentToDoItemListBinding>(FragmentToDoItemListBinding::inflate) {

    private lateinit var vm: MainViewModel
    private var toDoAdapter = ToDoItemListAdapter()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vm = (activity as MainActivity).vm
        val connectivityManager =
            getSystemService(requireContext(), ConnectivityManager::class.java)
        initAdapter()
        init()
        initListeners()
        startWorker()
        val networkCallback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                super.onAvailable(network)
                vm.fetchToDoItem()
            }
        }
        connectivityManager?.registerDefaultNetworkCallback(networkCallback)
    }

    private fun startWorker() {

    }

    private fun init() {

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                vm.toDoItemListFlow.collect { resource ->
                    when (resource) {
                        is Resource.Success -> {
                            toDoAdapter.submitList(resource.data)
                        }

                        is Resource.Error -> {
                            showError(resource.message)
                        }

                        is Resource.Loading -> showLoading()
                    }
                }
            }
        }
    }

    private fun showLoading() {

    }

    private fun initListeners() {
        binding.fab.setOnClickListener {
            val bundle = Bundle()
            bundle.apply {
                putSerializable("toDoItem", null)
                putSerializable("editFlag", false)

            }
            findNavController().navigate(
                R.id.action_toDoItemListFragment_to_toDoAddFragment,
                bundle
            )
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

    private fun showError(message: Int?) {
        when (message) {
            -1, 404, 500 -> {
                Snackbar.make(
                    requireView(),
                    Mapper(requireContext()).errorMapper(message),
                    Snackbar.LENGTH_INDEFINITE
                ).apply {
                    setAction(getString(R.string.refresh)) {
                        vm.fetchToDoItem()
                    }
                }.show()
            }

            else -> Snackbar.make(
                requireView(),
                Mapper(requireContext()).errorMapper(message),
                Snackbar.LENGTH_INDEFINITE
            ).show()
        }
    }
}



