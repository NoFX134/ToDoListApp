package ru.yandexschool.todolist.di.module

import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.yandexschool.todolist.data.remote.AuthInterceptor
import ru.yandexschool.todolist.di.scope.ApplicationScope
import ru.yandexschool.todolist.utils.ListRevisionStorage

@Module
class NetworkModule {

    companion object {
        private const val BASE_URL = "https://beta.mrdekk.ru/todobackend/"
    }


    @ApplicationScope
    @Provides
    fun providesAuthInterceptor(listRevisionStorage: ListRevisionStorage): Interceptor =
        AuthInterceptor(listRevisionStorage)

    @ApplicationScope
    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return loggingInterceptor
    }

    @ApplicationScope
    @Provides
    fun provideOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
        authInterceptor: AuthInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(authInterceptor)
            .build()
    }

    @ApplicationScope
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()
}
