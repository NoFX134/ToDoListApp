package ru.yandexschool.todolist.di.component

import dagger.Module

@Module(
    subcomponents = [
        ToDoItemListFragmentComponent::class,
        ToDoAddFragmentComponent::class]
)
class AppSubcomponents