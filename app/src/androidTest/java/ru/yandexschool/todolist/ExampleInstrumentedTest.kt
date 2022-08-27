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
        Thread.sleep(5000)
        onView(withId(R.id.fab)).perform(click())
        onView(withId(R.id.et_to_do)).perform(typeText("New Task #1"))
        closeSoftKeyboard()
        onView(withId(R.id.sp_importance)).perform(click())
        onData(allOf(`is`(instanceOf(String::class.java)), `is`("!!Высокий"))).perform(click())
        onView(withId(R.id.sw_date_picker)).perform(click())
        onView(withClassName(equalTo(DatePicker::class.java.name))).perform(
            PickerActions.setDate(
                2022,
                8,
                29
            )
        )
        onView(withText("OK")).perform(click())
        onView(withId(R.id.tv_save)).perform(click())

    }
}


