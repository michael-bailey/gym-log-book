package io.github.michael_bailey.gym_log_book.app.preferences

import io.github.michael_bailey.gym_log_book.lib.DebugPreferencesManager
import io.github.michael_bailey.gym_log_book.lib.PreferencesManager

interface IAppPreference {
	val appPreferencesManager: PreferencesManager
	val appDebugPreferencesManager: DebugPreferencesManager
}