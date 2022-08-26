package ru.yandexschool.todolist.utils

import mockwebserver3.Dispatcher
import mockwebserver3.MockResponse
import mockwebserver3.RecordedRequest
import java.net.HttpURLConnection

class ToDoItemDispatcher: Dispatcher() {

    override fun dispatch(request: RecordedRequest): MockResponse {
        return when (request.path){
            "list" -> {
                MockResponse()
                    .setResponseCode(HttpURLConnection.HTTP_OK)
                    .setBody(getJson("list.json"))
            }

            else -> {throw IllegalArgumentException("Unknown Request Path ${request.path.toString()}")}
        }
    }

}
