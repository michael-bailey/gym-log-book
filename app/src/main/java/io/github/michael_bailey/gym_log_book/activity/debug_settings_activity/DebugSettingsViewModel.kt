package io.github.michael_bailey.gym_log_book.activity.debug_settings_activity

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.github.michael_bailey.gym_log_book.App
import io.github.michael_bailey.gym_log_book.extension.component_activity.*

/**
 * View model for DebugSettingsActivity
 */
class DebugSettingsViewModel(
	val application: App,
) : AndroidViewModel(
	application
) {

	val _isDebugEnabled: MutableLiveData<Boolean> =
		MutableLiveData(application.isDebugEnabled())
	val isDebugEnabled: LiveData<Boolean>
		get() = _isDebugEnabled

	val _isBottomNavEnabled: MutableLiveData<Boolean> =
		MutableLiveData(application.isDebugEnabled())
	val isBottomNavEnabled: LiveData<Boolean>
		get() = _isBottomNavEnabled

	val _isStatusColourEnabled: MutableLiveData<Boolean> =
		MutableLiveData(application.isDebugEnabled())
	val isStatusColourEnabled: LiveData<Boolean>
		get() = _isStatusColourEnabled

	val _isNavbarColourEnabled: MutableLiveData<Boolean> =
		MutableLiveData(application.isDebugEnabled())
	val isNavbarColourEnabled: LiveData<Boolean>
		get() = _isNavbarColourEnabled

	fun setIsDebugEnabled(value: Boolean) {
		application.setIsDebugEnabled(value)
		_isDebugEnabled.value = value
	}

	fun setIsBottomNavEnabled(value: Boolean) {
		application.setIsDebugBottomNavBarEnabled(value)
		_isBottomNavEnabled.value = value
	}

	fun setIsStatusColourEnabled(value: Boolean) {
		application.setIsDebugStatusBarColourEnabled(value)
		_isStatusColourEnabled.value = value
	}

	fun setIsNavbarColourEnabled(value: Boolean) {
		application.setIsDebugNavBarColourEnabled(value)
		_isNavbarColourEnabled.value = value
	}
}

