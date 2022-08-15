package ru.yandexschool.todolist.di.module

import dagger.Module
import dagger.android.ContributesAndroidInjector
import ru.yandexschool.todolist.di.scope.ActivityScope
import ru.yandexschool.todolist.presentation.ui.MainActivity

@Module
abstract class MainActivityModule {

    @ActivityScope
    @ContributesAndroidInjector
    abstract fun bindMainActivity(): MainActivity
}