package ru.yandexschool.todolist.data

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentCaptor
import org.mockito.Mockito.`when`
import org.mockito.kotlin.any
import org.mockito.kotlin.inOrder
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import ru.yandexschool.todolist.data.model.ToDoItemDto
import ru.yandexschool.todolist.utils.TestData.fetchItemTestFlow
import ru.yandexschool.todolist.utils.TestData.getCountTestFlow
import ru.yandexschool.todolist.utils.TestData.listNewItemTest
import ru.yandexschool.todolist.utils.TestData.listOldItemTest
import ru.yandexschool.todolist.utils.TestData.listToDoItemApiTest
import ru.yandexschool.todolist.utils.TestData.toDoItemDtoTestList
import ru.yandexschool.todolist.utils.TestData.toDoItemTestOne




@OptIn(ExperimentalCoroutinesApi::class)
internal class ToDoItemRepositoryTest : BaseTest() {

    private lateinit var testRepository: ToDoItemRepository

    @Before
    fun setup() {
        testRepository = ToDoItemRepository(localDataSource, remoteDataSource)
    }

    @Test
    fun `fetch item when the data from the api are empty`() = runTest {
        `when`(remoteDataSource.fetchItemToApi()).thenReturn(emptyList())
        `when`(localDataSource.fetchItemToDb()).thenReturn(fetchItemTestFlow)
        testRepository.fetchItem()
        val inOrder = inOrder(remoteDataSource, localDataSource)
        inOrder.verify(remoteDataSource).fetchItemToApi()
        inOrder.verify(localDataSource).fetchItemToDb()
        verify(localDataSource, times(0)).insertToDoItemList(any())
        verify(localDataSource, times(0)).deleteOldToDoItem(any())
    }

    @Test
    fun `fetch item when the data from the api are not empty`() = runTest {
        `when`(remoteDataSource.fetchItemToApi()).thenReturn(toDoItemDtoTestList)
        `when`(localDataSource.fetchItemToDb()).thenReturn(fetchItemTestFlow)
        testRepository.fetchItem()
        val inOrder = inOrder(remoteDataSource, localDataSource)
        inOrder.verify(remoteDataSource).fetchItemToApi()
        inOrder.verify(localDataSource).insertToDoItemList(any())
        inOrder.verify(localDataSource).deleteOldToDoItem(any())
        inOrder.verify(localDataSource).fetchItemToDb()
    }

    @Test
    fun `when we add an item, the item is added to the database and the api`() = runTest {
        `when`(localDataSource.addToDoItemToDb(toDoItemTestOne)).thenReturn(Unit)
        `when`(remoteDataSource.addTodoItemApi(toDoItemTestOne)).thenReturn(Unit)
        testRepository.addItem(toDoItemTestOne)
        verify(localDataSource).addToDoItemToDb(toDoItemTestOne)
        verify(remoteDataSource).addTodoItemApi(toDoItemTestOne)
    }

    @Test
    fun `when we refresh an item, the item is refreshed to the database and the api`() = runTest {
        `when`(localDataSource.refreshItemToDb(toDoItemTestOne)).thenReturn(Unit)
        `when`(remoteDataSource.refreshToDoItemApi(toDoItemTestOne)).thenReturn(Unit)
        testRepository.refreshItem(toDoItemTestOne)
        verify(localDataSource).refreshItemToDb(toDoItemTestOne)
        verify(remoteDataSource).refreshToDoItemApi(toDoItemTestOne)
    }

    @Test
    fun `get item from database and api merge and insert to database`() = runTest {
        val argument: ArgumentCaptor<ToDoItemDto> = ArgumentCaptor.forClass(ToDoItemDto::class.java)
        `when`(localDataSource.getOldItem()).thenReturn(listOldItemTest)
        `when`(localDataSource.getNewItem()).thenReturn(listNewItemTest)
        `when`(remoteDataSource.fetchItemToApi()).thenReturn(listToDoItemApiTest)
        testRepository.updateItem()
        //verify(remoteDataSource).updateTodoItemApi(argument.capture())

    }

    @Test
    fun `test when we get a count, we get flow(int) from the database`() {
        `when`(localDataSource.getCount()).thenReturn(getCountTestFlow)
        val actual = testRepository.getCount()
        verify(localDataSource).getCount()
        assertEquals(actual, getCountTestFlow)
    }
}



