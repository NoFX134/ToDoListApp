package ru.yandexschool.todolist.data.remote


import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor: Interceptor {
    private val token = "SaidaIarberos"
    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()
        requestBuilder.addHeader("X-Last-Known-Revision", "3")
            .addHeader("Content-Type","application/json'")
            .addHeader("Authorization", "Bearer $token")
        return chain.proceed(requestBuilder.build())
    }
}
