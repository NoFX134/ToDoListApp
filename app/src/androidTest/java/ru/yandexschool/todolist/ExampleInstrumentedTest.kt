package ru.yandexschool.todolist

import android.widget.DatePicker
import androidx.test.espresso.Espresso.*
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.*
import androidx.test.espresso.contrib.PickerActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.adevinta.android.barista.assertion.BaristaListAssertions.assertDisplayedAtPosition
import com.adevinta.android.barista.assertion.BaristaVisibilityAssertions.assertContains
import com.adevinta.android.barista.assertion.BaristaVisibilityAssertions.assertDisplayed
import com.adevinta.android.barista.interaction.BaristaClickInteractions.clickOn
import com.adevinta.android.barista.interaction.BaristaEditTextInteractions.typeTo
import com.adevinta.android.barista.interaction.BaristaKeyboardInteractions.closeKeyboard
import com.adevinta.android.barista.interaction.BaristaPickerInteractions.setDateOnPicker
import com.adevinta.android.barista.interaction.BaristaSpinnerInteractions.clickSpinnerItem
import org.hamcrest.Matchers.*
import org.hamcrest.core.Is.`is`
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import ru.yandexschool.todolist.presentation.ui.MainActivity

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4ClassRunner::class)
class ToDoItemListFragmentTest {

    @JvmField
    @Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)


    @Test
    fun addNewTask() {
        clickOn(R.id.fab)
        typeTo(R.id.et_to_do, "New Task #1")
        closeKeyboard()
        clickSpinnerItem(R.id.sp_importance, 1)
        assertDisplayed("Низкий")
        clickOn(R.id.sw_date_picker)
        setDateOnPicker(2022, 9, 1)
        clickOn(R.id.tv_save)
        assertDisplayedAtPosition(R.id.rv_to_do_recycler_view, 0, R.id.tv_toDoText, "New Task #1");
    }
}


