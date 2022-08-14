package ru.yandexschool.todolist.data.remote


import okhttp3.Interceptor
import okhttp3.Response
import ru.yandexschool.todolist.utils.ListRevisionStorage

/**
 * A class for adding Authorization HEADERS to Retrofit network requests
 */

class AuthInterceptor() : Interceptor {

    private val token = "SaidaIarberos"

    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()
        requestBuilder.addHeader("Content-Type", "application/json")
            .addHeader("Authorization", "Bearer $token")
        return chain.proceed(requestBuilder.build())
    }
}