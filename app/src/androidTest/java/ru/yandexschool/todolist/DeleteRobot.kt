package ru.yandexschool.todolist

import androidx.test.core.app.ApplicationProvider
import java.text.FieldPosition


fun delete(func: DeleteRobot.() -> Unit) = DeleteRobot().apply { func() }

class DeleteRobot : BaseRobot() {

    fun deleteTestTask(position: Int) {
        scrollToRecycleViewPosition(R.id.rv_to_do_recycler_view, position)
        clickRecycleViewPosition(R.id.rv_to_do_recycler_view, position)
        clickView(R.id.tv_delete)
    }
}