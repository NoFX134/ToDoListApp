package ru.yandexschool.todolist.presentation.ui.fragment

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
import ru.yandexschool.todolist.data.mapper.ErrorMapper
import ru.yandexschool.todolist.databinding.FragmentToDoItemListBinding
import ru.yandexschool.todolist.presentation.adapter.ToDoItemListAdapter
import ru.yandexschool.todolist.presentation.ui.MainActivity
import ru.yandexschool.todolist.presentation.ui.viewModels.ToDoItemListViewModel
import ru.yandexschool.todolist.utils.ResponseState

/**
 * Fragment for display list toDoItem
 */

class ToDoItemListFragment :
    BaseFragment<FragmentToDoItemListBinding>(FragmentToDoItemListBinding::inflate) {

    private lateinit var vm: ToDoItemListViewModel
    private var toDoAdapter = ToDoItemListAdapter()
    private val connectivityManager by lazy {
        getSystemService(
            requireContext(),
            ConnectivityManager::class.java
        )
    }
    private val networkCallback by lazy {
        object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                super.onAvailable(network)
                vm.fetchToDoItem()
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        vm = (activity as MainActivity).vmList
        initAdapter()
        init()
        initListeners()
        connectivityManager?.registerDefaultNetworkCallback(networkCallback)
    }

    override fun onPause() {
        super.onPause()
        connectivityManager?.unregisterNetworkCallback(networkCallback)
    }

    private fun init() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                vm.toDoItemListFlow.collect { resource ->
                    when (resource) {
                        is ResponseState.Success -> {
                            toDoAdapter.submitList(resource.data)
                        }
                        is ResponseState.Error -> {
                            showError(resource.message)
                        }
                        is ResponseState.Loading -> showLoading()
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

    private fun showError(message: Int) {
        val errorMapper = ErrorMapper(requireContext())
        when (message) {
            -1, 404, 500 -> {
                Snackbar.make(
                    requireView(),
                    errorMapper.errorMapper(message),
                    Snackbar.LENGTH_INDEFINITE
                ).apply {
                    setAction(getString(R.string.refresh)) {
                        vm.fetchToDoItem()
                    }
                }.show()
            }
            else -> Snackbar.make(
                requireView(),
                errorMapper.errorMapper(message),
                Snackbar.LENGTH_INDEFINITE
            ).show()
        }
    }
}