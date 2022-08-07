package ru.yandexschool.todolist.data.remote

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import ru.yandexschool.todolist.data.model.ListItem
import ru.yandexschool.todolist.data.model.PostToDo
import ru.yandexschool.todolist.data.model.ResponseToDo
import ru.yandexschool.todolist.data.model.ToDoItem

interface Api {

    @GET("list")
    suspend fun fetchToDoItemList(): ResponseToDo

    @POST("list")
    suspend fun addToDoItemList(@Body toDoItem: PostToDo): Response<PostToDo>


}