package ru.yandexschool.todolist.data.remote


import okhttp3.Interceptor
import okhttp3.Response
import ru.yandexschool.todolist.utils.ListRevisionStorage

/**
 * A class for adding Revision HEADERS to Retrofit network requests
 */

class RevisionInterceptor(private val listRevisionStorage: ListRevisionStorage) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()
        val revision = listRevisionStorage.get()
        requestBuilder.addHeader("X-Last-Known-Revision", revision)
            .addHeader("Content-Type", "application/json")
        return chain.proceed(requestBuilder.build())
    }
}