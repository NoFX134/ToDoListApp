package ru.yandexschool.todolist.data

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import mockwebserver3.MockWebServer
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.junit.After
import org.junit.Before
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.yandexschool.todolist.data.local.ToDoItemDao
import ru.yandexschool.todolist.data.local.ToDoItemDatabase
import ru.yandexschool.todolist.data.remote.Api
import ru.yandexschool.todolist.utils.ToDoItemDispatcher
import java.io.IOException
import java.util.concurrent.TimeUnit

open class BaseTest {

    private lateinit var mockWebServer: MockWebServer
    lateinit var apiTest: Api
    private lateinit var okHttpClient: OkHttpClient
    private lateinit var loggingInterceptor: HttpLoggingInterceptor
    private lateinit var dbTest: ToDoItemDatabase
    lateinit var toDoItemDaoTest: ToDoItemDao

    @Before
    open fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        dbTest = Room.inMemoryDatabaseBuilder(context, ToDoItemDatabase::class.java).build()
        toDoItemDaoTest = dbTest.toDoItemDao()
        mockWebServer = MockWebServer()
        mockWebServer.dispatcher = ToDoItemDispatcher()
        mockWebServer.start()
        loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        okHttpClient = buildOkhttpClient(loggingInterceptor)

        apiTest = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(apiTest::class.java)
    }

    @After
    @Throws(IOException::class)
    fun tearDown() =
        runBlocking(Dispatchers.IO) {
            dbTest.clearAllTables()
            dbTest.close()
            mockWebServer.shutdown()
        }


    private fun buildOkhttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .build()
    }
}
