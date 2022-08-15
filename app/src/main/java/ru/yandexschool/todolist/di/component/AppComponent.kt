package ru.yandexschool.todolist.di.component

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import ru.yandexschool.todolist.App
import ru.yandexschool.todolist.di.module.*
import ru.yandexschool.todolist.di.scope.ApplicationScope
import ru.yandexschool.todolist.presentation.ui.MainActivity


@Component(
    modules = [
        AppModule::class,
        NetworkModule::class,
        ApiModule::class,
        AndroidInjectionModule::class,
        FragmentModule::class,
        MainActivityModule::class
    ]
)
@ApplicationScope
interface AppComponent {

    fun inject(app: App)

    @Component.Factory
    interface Factory {

        fun create(@BindsInstance application: Application): AppComponent
    }
}