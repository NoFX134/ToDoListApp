package ru.yandexschool.todolist.presentation.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.yandexschool.todolist.data.ToDoItemRepository
import ru.yandexschool.todolist.data.model.ToDoItem

class ItemListViewModel(private val toDoItemRepository: ToDoItemRepository) : ViewModel() {

    private val _toDoItemListLiveData: MutableLiveData<List<ToDoItem>> = MutableLiveData()
    val toDoItemListLiveData: LiveData<List<ToDoItem>> = _toDoItemListLiveData

    init {
        _toDoItemListLiveData.postValue(toDoItemRepository.fetchToDoItem())
    }

    fun addToDoItem(toDoItem: ToDoItem) {
        toDoItemRepository.addTodoItem(toDoItem)
        _toDoItemListLiveData.postValue(toDoItemRepository.fetchToDoItem())
    }

    fun deleteToDoItem(toDoItem: ToDoItem) {
        toDoItemRepository.deleteTodoItem(toDoItem)
    }

    fun editToDoItem(toDoItem: ToDoItem) {
        toDoItemRepository.editTodoItem(toDoItem)

    }



}