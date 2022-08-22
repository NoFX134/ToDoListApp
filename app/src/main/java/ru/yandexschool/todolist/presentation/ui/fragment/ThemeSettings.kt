package ru.yandexschool.todolist.presentation.ui.fragment

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import ru.yandexschool.todolist.R

class ThemeSettings : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)

    }
}