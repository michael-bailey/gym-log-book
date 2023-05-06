package io.github.michael_bailey.gym_log_book.lib.gatekeeper

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.github.michael_bailey.gym_log_book.App
import io.github.michael_bailey.gym_log_book.extension.application.preferences

/**
 * a gatekeeper manager to handle semi dynamic kill-switches
 */
object Gatekeeper {
	val application: App by lazy { App.getInstance() }

	/** list of defined gatekeepers*/
	val gatekeeperList: List<Pair<String, Boolean>> = listOf(
		"new_exercise_selector" to false,
		"database_exercise_item_view" to false,
		"database_exercise_type_view" to false,
		"database_weight_item_view" to false,
	)

	/**
	 * source of truth for all gatekeeper state
	 * it is loaded on boot
	 */
	private val gatekeeperMap: MutableMap<String, MutableLiveData<Boolean>> =
		loadGatekeepers()

	/* functions to fetch gatekeepers */
	fun eval(name: String): Boolean = application.appDebugPreferencesManager
		.isDebugEnabled.value!! && gatekeeperMap[name]?.value ?: false

	fun evalState(name: String): LiveData<Boolean>? = gatekeeperMap[name]

	fun setGatekeeper(name: String, value: Boolean) {
		gatekeeperMap[name]?.value = value
		application.preferences().edit().putBoolean("gk_$name", value).commit()
	}

	private fun getDefaults(): Map<String, Boolean> = gatekeeperList.toMap()

	fun loadGatekeepers(): MutableMap<String, MutableLiveData<Boolean>> =
		getDefaults()
			.map {
				it.key to MutableLiveData(
					application.preferences().getBoolean("gk_${it.key}", it.value)
				)
			}.toMap() as MutableMap<String, MutableLiveData<Boolean>>


	fun reset() {
		getDefaults().forEach {
			setGatekeeper(it.key, it.value)
		}
	}

}