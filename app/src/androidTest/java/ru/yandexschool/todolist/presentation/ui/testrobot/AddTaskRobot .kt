package ru.yandexschool.todolist.presentation.ui.testrobot

import androidx.test.core.app.ApplicationProvider
import ru.yandexschool.todolist.App
import ru.yandexschool.todolist.R

fun addTask(func: AddTaskRobot.() -> Unit) = AddTaskRobot().apply { func() }

class AddTaskRobot : BaseRobot() {

    private val app: App = ApplicationProvider.getApplicationContext()

    fun taskDescription(text: String) {
        typeEditText(R.id.et_to_do, text)
    }

    fun important(position: Int) {
        clickSpinner(R.id.sp_importance, position)
        checkTextDisplayed(app.resources.getStringArray(R.array.importance_array)[position])
    }

    fun deadlineDate(year: Int, month: Int, day: Int) {
        clickView(R.id.sw_date_picker)
        setDateInDatePicker(year, month, day)
    }

    fun save() {
        clickView(R.id.tv_save)
    }

    fun delete() {
        clickView(R.id.tv_delete)
    }

    fun close() {
        clickView(R.id.iv_close)
    }

}