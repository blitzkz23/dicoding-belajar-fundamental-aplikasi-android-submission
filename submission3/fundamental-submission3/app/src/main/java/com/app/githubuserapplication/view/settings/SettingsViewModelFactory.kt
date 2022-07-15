package com.app.githubuserapplication.view.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.app.githubuserapplication.view.main.MainViewModel

class SettingsViewModelFactory(private val pref: SettingsPreferences) : ViewModelProvider.NewInstanceFactory() {
	@Suppress("UNCHECKED_CAST")
	override fun <T : ViewModel?> create(modelClass: Class<T>): T {
		if (modelClass.isAssignableFrom(ThemeSettingsViewModel::class.java)) {
			return ThemeSettingsViewModel(pref) as T
		} else if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
			return MainViewModel(pref) as T
		}
		throw IllegalArgumentException("Uknown ViewModel class:" + modelClass.name)
	}
}