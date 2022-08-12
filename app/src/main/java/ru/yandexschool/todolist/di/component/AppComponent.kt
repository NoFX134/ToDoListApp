package ru.yandexschool.todolist.di.component

import android.app.Application
import android.content.Context
import dagger.BindsInstance
import dagger.Component
import ru.yandexschool.todolist.di.module.AppModule
import ru.yandexschool.todolist.di.scope.ApplicationScope

@ApplicationScope
@Component
interface AppComponent {

    @Component.Factory
    interface Factory {

        fun create(@BindsInstance context: Context): AppComponent
    }
}