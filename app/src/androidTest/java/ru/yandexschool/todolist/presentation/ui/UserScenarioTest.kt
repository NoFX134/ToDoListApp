package ru.yandexschool.todolist.presentation.ui

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import ru.yandexschool.todolist.presentation.ui.testrobot.addTask
import ru.yandexschool.todolist.presentation.ui.testrobot.delete
import ru.yandexschool.todolist.presentation.ui.testrobot.taskList

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4ClassRunner::class)
class UserScenarioTest {

    @JvmField
    @Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)
    private val testAddTaskText = "New Test Task #1"
    private val testEditTaskTest = "New Test Edit Task #1"
    private val spinnerPosition = 1
    private val deadlineDate = arrayOf(2022, 9, 29)
    private val editTaskPosition = 0

    @Test
    fun addNewTaskAndEdit() {
        taskList {
            clickAdd()
        }
        addTask {
            taskDescription(testAddTaskText)
            important(spinnerPosition)
            deadlineDate(deadlineDate[0], deadlineDate[1], deadlineDate[2])
            save()
        }
        taskList {
            taskAddOrEditSuccess(testAddTaskText, editTaskPosition)
            openTask(editTaskPosition)
        }
        addTask {
            taskDescription(testEditTaskTest)
            save()
        }
        taskList {
            taskAddOrEditSuccess(testEditTaskTest, editTaskPosition)
        }
        delete {
            deleteTestTask(editTaskPosition)
        }
    }

    @Test
    fun addNewTask() {
        taskList {
            clickAdd()
        }
        addTask {
            taskDescription(testAddTaskText)
            important(spinnerPosition)
            deadlineDate(deadlineDate[0], deadlineDate[1], deadlineDate[2])
            save()
        }
        taskList {
            taskAddOrEditSuccess(testAddTaskText, editTaskPosition)
            delete {
                deleteTestTask(editTaskPosition)
            }
        }
    }
}


