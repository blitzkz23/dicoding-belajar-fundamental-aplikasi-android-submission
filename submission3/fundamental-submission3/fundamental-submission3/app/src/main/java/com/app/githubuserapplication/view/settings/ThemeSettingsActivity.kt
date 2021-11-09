package com.app.githubuserapplication.view.settings

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.app.githubuserapplication.R
import com.google.android.material.switchmaterial.SwitchMaterial

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class ThemeSettingsActivity : AppCompatActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_theme_setting)

		val switchTheme: SwitchMaterial = findViewById(R.id.switch_theme)

		val pref = SettingsPreferences.getInstance(dataStore)

		val themeSettingsViewModel = ViewModelProvider(this, SettingsViewModelFactory(pref)).get(
			ThemeSettingsViewModel::class.java
		)
		supportActionBar?.setDisplayHomeAsUpEnabled(true)

		themeSettingsViewModel.getThemeSettings().observe(this, { isLightModeActive: Boolean ->
			if (isLightModeActive) {
				AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
				switchTheme.isChecked = true
			} else {
				AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
				switchTheme.isChecked = false
			}
		})

		switchTheme.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
			themeSettingsViewModel.saveThemeSetting(isChecked)
		}

	}
}