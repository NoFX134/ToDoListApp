package ru.yandexschool.todolist.di.component

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import ru.yandexschool.todolist.App
import ru.yandexschool.todolist.di.module.ApiModule
import ru.yandexschool.todolist.di.module.AppModule
import ru.yandexschool.todolist.di.module.DatabaseModule
import ru.yandexschool.todolist.di.module.NetworkModule
import ru.yandexschool.todolist.di.scope.ApplicationScope
import ru.yandexschool.todolist.presentation.ui.MainActivity


@Component(
    modules = [
        AppModule::class,
        NetworkModule::class,
        ApiModule::class,
        DatabaseModule::class,
        AppSubcomponents::class
    ]
)
@ApplicationScope
interface AppComponent {

    fun inject(app: App)
    fun toDoItemListFragmentComponent(): ToDoItemListFragmentComponent.Factory
    fun toDoAddFragmentComponent(): ToDoAddFragmentComponent.Factory

    @Component.Factory
    interface Factory {

        fun create(@BindsInstance application: Application): AppComponent
    }
}