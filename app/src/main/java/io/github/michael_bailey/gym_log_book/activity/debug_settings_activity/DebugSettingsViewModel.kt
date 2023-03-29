package io.github.michael_bailey.gym_log_book.activity.debug_settings_activity

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import io.github.michael_bailey.gym_log_book.App
import io.github.michael_bailey.gym_log_book.extension.component_activity.isDebugEnabled
import io.github.michael_bailey.gym_log_book.extension.component_activity.setIsDebugEnabled

/**
 * View model for DebugSettingsActivity
 */
class DebugSettingsViewModel(
	val application: App,
) : AndroidViewModel(
	application
) {
	private val _isDebugEnabled = mutableStateOf(application.isDebugEnabled())
	val exerciseListState: Boolean
		get() = _isDebugEnabled.value


	fun setIsDebugEnabled(value: Boolean) {
		application.setIsDebugEnabled(value)
		_isDebugEnabled.value = value
	}
}

