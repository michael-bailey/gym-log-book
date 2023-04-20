package io.github.michael_bailey.gym_log_book.lib.gatekeeper

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.github.michael_bailey.gym_log_book.App

/**
 * a gatekeeper manager to handle semi dynamic kill-switches
 */
object Gatekeeper {
	val application: App by lazy { App.getInstance() }

	/** list of defined gatekeepers*/
	val gatekeeperList: List<Pair<String, Boolean>> = listOf(
		"new_exercise_selector" to false,
	)

	private val gatekeeperMap: MutableMap<String, MutableLiveData<Boolean>> =
		getDefaults()

	/* functions to fetch gatekeepers */
	fun eval(name: String): Boolean = application.appDebugPreferencesManager
		.isDebugEnabled.value!! && gatekeeperMap[name]?.value ?: false

	fun evalState(name: String): LiveData<Boolean>? = gatekeeperMap[name]

	fun setGatekeeper(name: String, value: Boolean) {
		gatekeeperMap[name]?.value = value
	}

	private fun getDefaults() = gatekeeperList
		.map { it.first to MutableLiveData(it.second) }
		.toMap() as MutableMap<String, MutableLiveData<Boolean>>

	fun reset() {
		getDefaults().forEach {
			setGatekeeper(it.key, it.value.value!!)
		}
	}

}