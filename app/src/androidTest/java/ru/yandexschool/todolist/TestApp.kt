package ru.yandexschool.todolist

import ru.yandexschool.todolist.di.TestAppComponent


class TestApp: App() {
    lateinit var testAppComponent: TestAppComponent
        private set

    override fun onCreate() {
        super.onCreate()
        testAppComponent

    }
}