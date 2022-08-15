package ru.yandexschool.todolist.di.module

import dagger.Module
import dagger.android.ContributesAndroidInjector
import ru.yandexschool.todolist.di.scope.FragmentScope
import ru.yandexschool.todolist.presentation.ui.fragment.ToDoAddFragment
import ru.yandexschool.todolist.presentation.ui.fragment.ToDoItemListFragment

@Module
abstract class FragmentModule {

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun contributeToDoAddFragment(): ToDoAddFragment

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun contributeToDoListItemFragment(): ToDoItemListFragment
}