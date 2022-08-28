package ru.yandexschool.todolist.data.mapper

import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import ru.yandexschool.todolist.App
import ru.yandexschool.todolist.data.utils.TestData

@RunWith(AndroidJUnit4::class)
class DataMapperTest  {

    private val app: App = ApplicationProvider.getApplicationContext()
    private val dataClassMapper = DataClassMapper(app)

    @Test
    fun mapListToDoItemDtoIntoListToDoItem() {
        val actual = dataClassMapper.listToDoItemDtoIntoListToDoItem(TestData.listToDoItemDto)
        val expected = TestData.listToDoItem
        assertEquals(expected, actual)
    }
    @Test
    fun mapListToDoItemDtoIntoResponseToDO() {
        val actual = dataClassMapper.listToDoItemDtoIntoResponseToDo(TestData.listToDoItemDto)
        val expected = TestData.responseToDo
        assertEquals(expected, actual)
    }

    @Test
    fun mapToDoItemToPostToDo() {
        val actual = dataClassMapper.toDoItemToPostToDo(TestData.toDoItem)
        val expected = TestData.postToDo
        assertEquals(expected, actual)
    }

    @Test
    fun mapListItemIntoToDoItemDto(){
        val actual = dataClassMapper.listItemIntoToDoItemDto(TestData.listItem)
        val expected = TestData.toDoItemDto
        assertEquals(expected, actual)
    }

    @Test
    fun toDoItemIntoToDoItemDto(){
        val actual = dataClassMapper.toDoItemIntoToDoItemDto(TestData.toDoItem)
        val expected = TestData.toDoItemDto
        assertEquals(expected, actual)
    }
}


