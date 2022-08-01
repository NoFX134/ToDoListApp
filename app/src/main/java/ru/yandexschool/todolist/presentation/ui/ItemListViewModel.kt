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
        _toDoItemListLiveData.postValue(toDoItemRepository.toDoItemList)
    }

    fun addToDoItem(toDoItem: ToDoItem, position:Int) {
        if (position==-1) {
            toDoItemRepository.addTodoItem(toDoItem)
            Log.d("TAG1", toDoItemRepository.toDoItemList.toString())
            _toDoItemListLiveData.postValue(toDoItemRepository.toDoItemList)
        }
        else{
            toDoItemRepository.editTodoItem(toDoItem,position)
        }
    }

}