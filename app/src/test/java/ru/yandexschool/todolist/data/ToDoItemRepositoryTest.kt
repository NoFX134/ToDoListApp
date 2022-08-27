package ru.yandexschool.todolist.data

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.kotlin.*
import ru.yandexschool.todolist.data.local.ToDoItemLocalDataSource
import ru.yandexschool.todolist.data.remote.ToDoItemRemoteDataSource
import ru.yandexschool.todolist.utils.TestData.fetchItemTestFlow
import ru.yandexschool.todolist.utils.TestData.getCountTestFlow
import ru.yandexschool.todolist.utils.TestData.listNewItemTest
import ru.yandexschool.todolist.utils.TestData.listOldItemTest
import ru.yandexschool.todolist.utils.TestData.listToDoItemApiTest
import ru.yandexschool.todolist.utils.TestData.mergedList
import ru.yandexschool.todolist.utils.TestData.toDoItemDtoTestList
import ru.yandexschool.todolist.utils.TestData.toDoItemTestOne

@OptIn(ExperimentalCoroutinesApi::class)
class ToDoItemRepositoryTest {

    private val remoteDataSource = mock<ToDoItemRemoteDataSource>()
    private val localDataSource = mock<ToDoItemLocalDataSource>()
    private val testRepository = ToDoItemRepository(localDataSource, remoteDataSource)

    /**
     * Тест проверяет, что при отсутствии данных с API отдаются данные с БД
     */
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

    /**
     * Тест проверяет, что при наличии данных с API сначала обнавляется БД потом отдаются данные с БД
     */
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

    /**
     * Тест проверяет, что при добавлении задачи, вызываются методы добавления в БД и АPI и проверяет,
     * что задача переданная в метод, передается без изменений в методы
     * addToDoItemToDb() и addTodoItemApi()
     */
    @Test
    fun `when we add an item, the item is added to the database and the api`() = runTest {
        testRepository.addItem(toDoItemTestOne)
        verify(localDataSource).addToDoItemToDb(toDoItemTestOne)
        verify(remoteDataSource).addTodoItemApi(toDoItemTestOne)
    }

    /**
     * Тест проверяет, что при обновлении задачи, вызываются методы обновлении БД и АPI и проверяет,
     * что задача переданная в метод, передается без изменений в методы
     * refreshItemToDb() и refreshTodoItemApi
     */
    @Test
    fun `when we refresh an item, the item is refreshed to the database and the api`() = runTest {
        testRepository.refreshItem(toDoItemTestOne)
        verify(localDataSource).refreshItemToDb(toDoItemTestOne)
        verify(remoteDataSource).refreshToDoItemApi(toDoItemTestOne)
    }

    /**
     * Тест проверяет, что при удалении задачи, вызываются методы удаления из БД и АPI и проверяет,
     * что задача переданная в метод, передается без изменений в методы
     * deleteToDoItemToDb() и deleteTodoItemApi()
     */
    @Test
    fun `when we delete an item, the item is refreshed to the database and the api`() = runTest {
        testRepository.deleteItem(toDoItemTestOne)
        verify(localDataSource).deleteItemToDb(toDoItemTestOne)
        verify(remoteDataSource).deleteTodoItemApi(toDoItemTestOne.id)
    }

    /**
     * Тест проверяет, что при обновлении данных на сервер передаётся merge данных с БД и API
     */
    @Test
    fun `get item from database and api merge and insert to database`() = runTest {
        `when`(localDataSource.getOldItem()).thenReturn(listOldItemTest)
        `when`(localDataSource.getNewItem()).thenReturn(listNewItemTest)
        `when`(remoteDataSource.fetchItemToApi()).thenReturn(listToDoItemApiTest)
        testRepository.updateItem()
        verify(remoteDataSource).updateTodoItemApi(mergedList)
    }

    /**
     * Тест проверяет, что данные о количестве записей, получаемых с БД, не изменяются в репозитории
     */
    @Test
    fun `test when we get a count, we get flow(int) from the database`() {
        `when`(localDataSource.getCount()).thenReturn(getCountTestFlow)
        val actual = testRepository.getCount()
        verify(localDataSource).getCount()
        assertEquals(actual, getCountTestFlow)
    }
}