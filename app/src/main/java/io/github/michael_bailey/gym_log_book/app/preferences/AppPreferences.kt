package io.github.michael_bailey.gym_log_book.app.preferences

import io.github.michael_bailey.gym_log_book.app.App
import io.github.michael_bailey.gym_log_book.lib.DebugPreferencesManager
import io.github.michael_bailey.gym_log_book.lib.PreferencesManager

class AppPreferences(
	val companion: App.Companion
) : IAppPreference {
	override val appPreferencesManager by
	lazy { PreferencesManager(companion.getInstance()) }
	override val appDebugPreferencesManager by
	lazy { DebugPreferencesManager(companion.getInstance()) }
}