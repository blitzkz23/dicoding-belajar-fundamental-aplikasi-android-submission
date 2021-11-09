package com.app.githubuserapplication.view.settings

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SettingsPreferences private constructor(private val dataStore: DataStore<Preferences>) {

	fun getThemeSetting(): Flow<Boolean> {
		return dataStore.data.map { preferences ->
			preferences[THEME_KEY] ?: false
		}
	}

	suspend fun saveThemeSetting(isLightModeActive: Boolean) {
		dataStore.edit { preferences ->
			preferences[THEME_KEY] = isLightModeActive
		}
	}

	companion object {
		private val THEME_KEY = booleanPreferencesKey("theme_setting")

		@Volatile
		private var INSTANCE: SettingsPreferences? = null

		fun getInstance(dataStore: DataStore<Preferences>): SettingsPreferences {
			return INSTANCE ?: synchronized(this) {
				val instance = SettingsPreferences(dataStore)
				INSTANCE = instance
				instance
			}
		}
	}
}