package ru.yandexschool.todolist.di

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import ru.yandexschool.todolist.App
import ru.yandexschool.todolist.di.component.AppComponent

@Component
interface TestAppComponent: AppComponent {
    override fun inject(app: App)

    @Component.Factory
    interface Factory {

        fun create(@BindsInstance application: Application): TestAppComponent
    }
}
