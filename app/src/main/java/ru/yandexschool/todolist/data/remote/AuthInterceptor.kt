package ru.yandexschool.todolist.data.remote


import okhttp3.Interceptor
import okhttp3.Response
import ru.yandexschool.todolist.utils.ListRevisionStorage
import javax.inject.Inject

/**
 * A class for adding HEADERS to Retrofit network requests
 */

class AuthInterceptor @Inject constructor(private val listRevisionStorage: ListRevisionStorage) : Interceptor {

    private val token = "SaidaIarberos"

    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()
        val revision = listRevisionStorage.get()
        requestBuilder.addHeader("X-Last-Known-Revision", revision)
            .addHeader("Content-Type", "application/json")
            .addHeader("Authorization", "Bearer $token")
        return chain.proceed(requestBuilder.build())
    }
}