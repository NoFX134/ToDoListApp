package ru.yandexschool.todolist.di.component

import dagger.Subcomponent
import ru.yandexschool.todolist.di.scope.FragmentScope
import ru.yandexschool.todolist.presentation.ui.fragment.ToDoAddFragment
import ru.yandexschool.todolist.presentation.ui.fragment.ToDoItemListFragment


@Subcomponent
@FragmentScope
interface ToDoItemListFragmentComponent {

    fun inject(fragment: ToDoItemListFragment)

    @Subcomponent.Factory
    interface Factory {

        fun create(): ToDoItemListFragmentComponent
    }

}