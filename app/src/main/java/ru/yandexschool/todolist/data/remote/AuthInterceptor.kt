package ru.yandexschool.todolist.data.remote


import okhttp3.Interceptor
import okhttp3.Response
import ru.yandexschool.todolist.utils.ListRevisionStorage
import javax.inject.Inject

/**
 * A class for adding HEADERS to Retrofit network requests
 */

class AuthInterceptor @Inject constructor() : Interceptor {

    private val token = "SaidaIarberos"

    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()
        requestBuilder.addHeader("Authorization", "Bearer $token")
        return chain.proceed(requestBuilder.build())
    }
}