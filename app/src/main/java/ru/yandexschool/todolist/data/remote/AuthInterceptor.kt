package ru.yandexschool.todolist.data.remote


import okhttp3.Interceptor
import okhttp3.Response
import ru.yandexschool.todolist.data.SharedPref

class AuthInterceptor(sharedPref: SharedPref) : Interceptor {

    private val revision = sharedPref.get()
    private val token = "SaidaIarberos"
    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()
        requestBuilder.addHeader("X-Last-Known-Revision", revision)
            .addHeader("Content-Type", "application/json")
            .addHeader("Authorization", "Bearer $token")
        return chain.proceed(requestBuilder.build())
    }
}
//