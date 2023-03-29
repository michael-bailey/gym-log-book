package io.github.michael_bailey.gym_log_book.activity.debug_settings_activity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import io.github.michael_bailey.gym_log_book.App

class DebugSettingsViewModelFactory(val application: App) :
	ViewModelProvider.Factory {
	override fun <T : ViewModel> create(modelClass: Class<T>): T {
		val a = DebugSettingsViewModel(application) as T
		return a
	}
}