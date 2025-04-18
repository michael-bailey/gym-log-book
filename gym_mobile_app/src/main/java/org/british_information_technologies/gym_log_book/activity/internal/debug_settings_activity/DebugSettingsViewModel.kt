package org.british_information_technologies.gym_log_book.activity.internal.debug_settings_activity

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.british_information_technologies.gym_log_book.lib.DebugPreferencesManager
import org.british_information_technologies.gym_log_book.lib.gatekeeper.Gatekeeper
import javax.inject.Inject

/**
 * View model for DebugSettingsActivity
 */
@HiltViewModel
class DebugSettingsViewModel @Inject constructor(
	private val applicationContext: Application,
	private val appDebugPreferencesManager: DebugPreferencesManager,
	private val gatekeeper: Gatekeeper,
) : ViewModel() {


	val isDebugEnabled = appDebugPreferencesManager.isDebugEnabled

	val isDebugStatusBarColourEnabled =
		appDebugPreferencesManager.isDebugStatusBarColourEnabled

	val isDebugNavBarColourEnabled =
		appDebugPreferencesManager.isDebugNavBarColourEnabled

	val isDebugBottomNavBarEnabled =
		appDebugPreferencesManager.isDebugBottomNavBarEnabled

	val gatekeeperList = gatekeeper.gatekeeperList

	fun setIsDebugEnabled(value: Boolean) {
		appDebugPreferencesManager.setIsDebugEnabled(value)
	}

	fun setIsBottomNavEnabled(value: Boolean) {
		appDebugPreferencesManager.setIsDebugBottomNavBarEnabled(value)
	}

	fun setIsStatusColourEnabled(value: Boolean) {
		appDebugPreferencesManager.setIsDebugStatusBarColourEnabled(value)
	}

	fun setIsNavbarColourEnabled(value: Boolean) {
		appDebugPreferencesManager.setIsDebugNavBarColourEnabled(value)
	}

	fun evalGatekeeper(name: String): LiveData<Boolean> {
		return gatekeeper.evalState(name).asLiveData()
	}

	fun setGatekeeper(name: String, value: Boolean) {
		viewModelScope.launch {
			gatekeeper.setGatekeeper(name, value)
		}
	}
}

