package ru.yandexschool.todolist.di.component

import dagger.Subcomponent
import ru.yandexschool.todolist.di.scope.FragmentScope
import ru.yandexschool.todolist.presentation.ui.fragment.ToDoAddFragment


@Subcomponent
@FragmentScope
interface ToDoAddFragmentComponent {

    fun inject(fragment: ToDoAddFragment)

    @Subcomponent.Factory
    interface Factory {

        fun create(): ToDoAddFragmentComponent
    }

}