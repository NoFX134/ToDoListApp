package ru.yandexschool.todolist.data

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import ru.yandexschool.todolist.data.local.ToDoItemLocalDataSource
import ru.yandexschool.todolist.data.mapper.DataClassMapper
import ru.yandexschool.todolist.data.remote.ToDoItemRemoteDataSource
import ru.yandexschool.todolist.utils.ListRevisionStorage
import ru.yandexschool.todolist.utils.UpdateTimeStorage
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
class ToDoItemRepositoryTest(
) : BaseTest() {

    @Inject
    private lateinit var updateTimeStorage: UpdateTimeStorage

    @Inject
    private lateinit var listRevisionStorage: ListRevisionStorage

    @Inject
    private lateinit var dataClassMapper: DataClassMapper

    private lateinit var testRepository: ToDoItemRepository

    @Before
    override fun setup() {
        super.setup()
        val remoteDataSourceTest = ToDoItemRemoteDataSource(
            apiTest,
            dataClassMapper,
            updateTimeStorage,
            listRevisionStorage
        )
        val localDataSourceTest = ToDoItemLocalDataSource(toDoItemDaoTest, dataClassMapper)
        testRepository = ToDoItemRepository(
            dataClassMapper,
            updateTimeStorage,
            localDataSourceTest,
            remoteDataSourceTest
        )
        testRepository = ToDoItemRepository(
            dataClassMapper,
            updateTimeStorage,
            localDataSourceTest,
            remoteDataSourceTest
        )

    }

    @Test
    fun `fetchData if data for api empty`() = runTest {
        assertEquals(0, 0)
    }

}


