package ru.yandexschool.todolist.presentation.ui.testrobot

import ru.yandexschool.todolist.R

fun taskList(func: TaskListRobot.() -> Unit) = TaskListRobot().apply { func() }

class TaskListRobot: BaseRobot() {

    fun taskAddOrEditSuccess(text: String, position: Int){
        checkRecycleViewItemMatches(R.id.rv_to_do_recycler_view, position, R.id.tv_toDoText, text)
    }
    fun clickAdd() {
        clickView(R.id.fab)
    }

    fun openTask(position: Int) {
        clickRecycleViewPosition(R.id.rv_to_do_recycler_view, position)
    }
}