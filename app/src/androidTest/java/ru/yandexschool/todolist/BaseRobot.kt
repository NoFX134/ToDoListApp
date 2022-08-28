package ru.yandexschool.todolist

import androidx.recyclerview.widget.RecyclerView
import com.adevinta.android.barista.assertion.BaristaListAssertions
import com.adevinta.android.barista.assertion.BaristaListAssertions.assertDisplayedAtPosition
import com.adevinta.android.barista.assertion.BaristaVisibilityAssertions.assertDisplayed
import com.adevinta.android.barista.interaction.BaristaClickInteractions.clickOn
import com.adevinta.android.barista.interaction.BaristaEditTextInteractions.clearText
import com.adevinta.android.barista.interaction.BaristaEditTextInteractions.typeTo
import com.adevinta.android.barista.interaction.BaristaListInteractions
import com.adevinta.android.barista.interaction.BaristaListInteractions.clickListItem
import com.adevinta.android.barista.interaction.BaristaListInteractions.scrollListToPosition
import com.adevinta.android.barista.interaction.BaristaPickerInteractions
import com.adevinta.android.barista.interaction.BaristaPickerInteractions.setDateOnPicker
import com.adevinta.android.barista.interaction.BaristaSpinnerInteractions
import com.adevinta.android.barista.interaction.BaristaSpinnerInteractions.clickSpinnerItem


open class BaseRobot {

    fun clickView(resId: Int) {
        clickOn(resId)
    }

    fun typeEditText(resId: Int, text: String) {
        clearText(resId)
        typeTo(resId, text)
    }

    fun clickSpinner(resId: Int, position: Int) {
        clickSpinnerItem(resId, position)
    }

    fun checkTextDisplayed(text: String) {
        assertDisplayed(text)
    }

    fun setDateInDatePicker(year: Int, month: Int, day: Int) {
        setDateOnPicker(year, month, day)
    }

    fun checkRecycleViewItemMatches(
        resIdRecycleView: Int,
        position: Int,
        resIdItem: Int,
        text: String
    ) {
        assertDisplayedAtPosition(resIdRecycleView, position, resIdItem, text)
    }

    fun scrollToRecycleViewPosition(resIdRecycleView: Int, position: Int) {
        scrollListToPosition(resIdRecycleView, position)
    }

    fun clickRecycleViewPosition(resIdRecycleView: Int, position: Int){
        clickListItem(resIdRecycleView, position)
    }
}