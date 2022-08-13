package ru.yandexschool.todolist.di.component

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import ru.yandexschool.todolist.di.module.ApiModule
import ru.yandexschool.todolist.di.module.AppModule
import ru.yandexschool.todolist.di.module.NetworkModule
import ru.yandexschool.todolist.di.scope.ApplicationScope
import ru.yandexschool.todolist.presentation.ui.MainActivity
import ru.yandexschool.todolist.presentation.ui.fragment.ToDoAddFragment
import ru.yandexschool.todolist.presentation.ui.fragment.ToDoItemListFragment
import ru.yandexschool.todolist.presentation.ui.viewModels.ToDoItemListViewModel

@ApplicationScope
@Component(
    modules = [
        AppModule::class,
        NetworkModule::class,
        ApiModule::class,
    ]
)
interface AppComponent {

    fun inject(activity: MainActivity)
    fun inject(fragment: ToDoItemListFragment)
    fun inject(fragment: ToDoAddFragment)

    @Component.Factory
    interface Factory {

        fun create(@BindsInstance application: Application): AppComponent

    }

}