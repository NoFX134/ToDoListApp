package ru.yandexschool.todolist.data.remote

import android.content.Context
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.yandexschool.todolist.data.SharedPref

const val BASE_URL =  "https://beta.mrdekk.ru/todobackend/"

class RetrofitInstance(private val sharedPref: SharedPref) {
    private val retrofit by lazy {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .addInterceptor(AuthInterceptor(sharedPref))
            .build()
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }
    val api: Api by lazy {
        retrofit.create(Api::class.java)
    }
}