package io.github.michael_bailey.gym_log_book.activity.debug_settings_activity

import androidx.lifecycle.AndroidViewModel
import io.github.michael_bailey.gym_log_book.App

/**
 * View model for DebugSettingsActivity
 */
class DebugSettingsViewModel(
	val app: App,
) : AndroidViewModel(
	app
) {

	val isDebugEnabled = app.appDebugPreferencesManager.isDebugEnabled
	val isDebugStatusBarColourEnabled =
		app.appDebugPreferencesManager.isDebugStatusBarColourEnabled
	val isDebugNavBarColourEnabled =
		app.appDebugPreferencesManager.isDebugNavBarColourEnabled
	val isDebugBottomNavBarEnabled =
		app.appDebugPreferencesManager.isDebugBottomNavBarEnabled

	fun setIsDebugEnabled(value: Boolean) {
		app.appDebugPreferencesManager.setIsDebugEnabled(value)
	}

	fun setIsBottomNavEnabled(value: Boolean) {
		app.appDebugPreferencesManager.setIsDebugBottomNavBarEnabled(value)
	}

	fun setIsStatusColourEnabled(value: Boolean) {
		app.appDebugPreferencesManager.setIsDebugStatusBarColourEnabled(value)
	}

	fun setIsNavbarColourEnabled(value: Boolean) {
		app.appDebugPreferencesManager.setIsDebugNavBarColourEnabled(value)
	}
}

