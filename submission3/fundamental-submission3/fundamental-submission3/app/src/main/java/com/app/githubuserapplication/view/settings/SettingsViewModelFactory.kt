package com.app.githubuserapplication.view.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class SettingsViewModelFactory(private val pref: SettingsPreferences) : ViewModelProvider.NewInstanceFactory() {
	@Suppress("UNCHECKED_CAST")
	override fun <T : ViewModel?> create(modelClass: Class<T>): T {
		if (modelClass.isAssignableFrom(ThemeSettingsViewModel::class.java)) {
			return ThemeSettingsViewModel(pref) as T
		}
		throw IllegalArgumentException("Uknown ViewModel class:" + modelClass.name)
	}
}