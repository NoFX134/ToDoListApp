package ru.yandexschool.todolist.data.remote

import retrofit2.Response
import retrofit2.http.*
import ru.yandexschool.todolist.data.model.ListItem
import ru.yandexschool.todolist.data.model.PostToDo
import ru.yandexschool.todolist.data.model.ResponseToDo
import ru.yandexschool.todolist.data.model.ToDoItem

/**
 * Retrofit interface for setting server requests
 */

interface Api {

    @GET("list")
    suspend fun fetchToDoItemList(): Response<ResponseToDo>

    @POST("list")
    suspend fun addToDoItem(@Body toDoItem: PostToDo): Response<PostToDo>

    @DELETE("list/{id}")
    suspend fun deleteToDoItem(@Path("id") toDoItemId: String): Response<PostToDo>

    @PUT("list/{id}")
    suspend fun refreshToDoItem(@Path("id") toDoItemId: String, @Body toDoItem: PostToDo): Response<PostToDo>
}