package com.app.githubuserapplication.view.viewmodels.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.app.githubuserapplication.view.preferences.SettingsPreferences
import com.app.githubuserapplication.view.viewmodels.ThemeSettingsViewModel

class SettingsViewModelFactory(private val pref: SettingsPreferences) : ViewModelProvider.NewInstanceFactory() {
	@Suppress("UNCHECKED_CAST")
	override fun <T : ViewModel?> create(modelClass: Class<T>): T {
		if (modelClass.isAssignableFrom(ThemeSettingsViewModel::class.java)) {
			return ThemeSettingsViewModel(pref) as T
		}
		throw IllegalArgumentException("Uknown ViewModel class:" + modelClass.name)
	}
}