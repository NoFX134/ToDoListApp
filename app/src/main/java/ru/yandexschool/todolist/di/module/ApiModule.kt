package ru.yandexschool.todolist.di.module

import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import ru.yandexschool.todolist.data.remote.Api
import ru.yandexschool.todolist.di.scope.ApplicationScope

@Module
class ApiModule {

    @Provides
    @ApplicationScope
    fun provideApi(retrofit: Retrofit): Api = retrofit.create(Api::class.java)

}