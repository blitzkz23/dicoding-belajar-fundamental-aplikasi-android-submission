package com.app.githubuserapplication.view.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.app.githubuserapplication.view.preferences.SettingsPreferences
import kotlinx.coroutines.launch

class ThemeSettingsViewModel(private val pref: SettingsPreferences): ViewModel() {
	fun getThemeSettings(): LiveData<Boolean> {
		return pref.getThemeSetting().asLiveData()
	}

	fun saveThemeSetting(isLightModeActive: Boolean) {
		viewModelScope.launch {
			pref.saveThemeSetting(isLightModeActive)
		}
	}
}