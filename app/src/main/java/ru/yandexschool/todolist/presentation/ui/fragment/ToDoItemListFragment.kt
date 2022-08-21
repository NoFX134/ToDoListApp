package ru.yandexschool.todolist.presentation.ui.fragment

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.os.Bundle
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.AnticipateInterpolator
import android.view.animation.BounceInterpolator
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.viewModels
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import androidx.interpolator.view.animation.LinearOutSlowInInterpolator
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import ru.yandexschool.todolist.App
import ru.yandexschool.todolist.R
import ru.yandexschool.todolist.data.mapper.ErrorMapper
import ru.yandexschool.todolist.databinding.FragmentToDoItemListBinding
import ru.yandexschool.todolist.presentation.adapter.ToDoItemListAdapter
import ru.yandexschool.todolist.presentation.ui.viewModels.ToDoItemListViewModel
import ru.yandexschool.todolist.presentation.ui.viewModels.ToDoItemListViewModelFactory
import javax.inject.Inject




/**
 * Fragment for display list toDoItem
 */

class ToDoItemListFragment :
    BaseFragment<FragmentToDoItemListBinding>(FragmentToDoItemListBinding::inflate) {

    private val vm: ToDoItemListViewModel by viewModels { factory.create() }

    @Inject
    lateinit var factory: ToDoItemListViewModelFactory.Factory

    @Inject
    lateinit var errorMapper: ErrorMapper

    @Inject
    lateinit var toDoAdapter: ToDoItemListAdapter

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
                vm.updateItem()
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (context.applicationContext as App).appComponent.toDoItemListFragmentComponent().create()
            .inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        init()
        initListeners()
        connectivityManager?.registerDefaultNetworkCallback(networkCallback)
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                vm.countFlow.collect { count ->
                    initAnimation(count)
                }
            }
        }
    }



private fun initAnimation(start: Int) {
    val from = 1.1f
    val to = 0.9f
    val scaleX = PropertyValuesHolder.ofFloat(View.SCALE_X, from, to)
    val scaleY = PropertyValuesHolder.ofFloat(View.SCALE_Y, from, to)
    val rotate = PropertyValuesHolder.ofFloat(View.ROTATION, 30F, 0F)
    val translationZ = PropertyValuesHolder.ofFloat(View.TRANSLATION_Z, from, to)
    val animator =
        ObjectAnimator.ofPropertyValuesHolder(binding.fab, scaleX, scaleY, rotate, translationZ)
            .apply {
                interpolator = LinearOutSlowInInterpolator()
                duration = 900L
                repeatCount = ValueAnimator.INFINITE
                repeatMode = ValueAnimator.REVERSE
            }
    if (start == 0) animator.start()
    else animator.cancel()

}

override fun onPause() {
    super.onPause()
    connectivityManager?.unregisterNetworkCallback(networkCallback)
}

private fun init() {
    viewLifecycleOwner.lifecycleScope.launch {
        repeatOnLifecycle(Lifecycle.State.STARTED) {
            vm.toDoItemListFlow.collect { toDoItemList ->
                toDoAdapter.submitList(toDoItemList)
            }
        }
    }
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
    toDoAdapter.checkBoxClickListener { toDoItem, done ->
        vm.setDone(toDoItem, done)
    }
}

private fun initAdapter() {
    binding.rvToDoRecyclerView.apply {
        adapter = toDoAdapter
        layoutManager = LinearLayoutManager(activity)
    }
}

private fun showError(message: Int) {
    when (message) {
        500 -> {
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
            Snackbar.LENGTH_SHORT
        ).show()
    }
}
}