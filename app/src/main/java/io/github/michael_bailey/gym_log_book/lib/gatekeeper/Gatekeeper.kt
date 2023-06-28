package io.github.michael_bailey.gym_log_book.lib.gatekeeper

import android.content.SharedPreferences
import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.onEach

/**
 * a gatekeeper manager to handle semi dynamic kill-switches
 */
class Gatekeeper(
	private val preferences: SharedPreferences
) {

	/** list of defined gatekeepers*/
	val gatekeeperList: List<Pair<String, Boolean>> = listOf(
		"new_exercise_selector" to false,
		"in_memory_database" to true,
		"database_exercise_item_view" to false,
		"database_exercise_type_view" to false,
		"database_weight_item_view" to false,
	)

	/**
	 * source of truth for all gatekeeper state
	 * it is loaded on boot
	 */
	private val gatekeeperMap: MutableMap<String, MutableStateFlow<Boolean>> by lazy { loadGatekeepers() }

	fun evalState(name: String): Flow<Boolean> = gatekeeperMap[name]!!.onEach {
		Log.d(
			"Gatekeeper",
			"got new gatekeeper value $name:$it"
		)
	}

	suspend fun setGatekeeper(name: String, value: Boolean) {

		preferences.edit().putBoolean("gk_$name", value).apply()
		gatekeeperMap[name]!!.emit(value)
	}

	private fun getDefaults(): Map<String, Boolean> = gatekeeperList.toMap()

	private fun loadGatekeepers(): MutableMap<String, MutableStateFlow<Boolean>> =
		getDefaults()
			.map {
				val flow =
					MutableStateFlow(preferences.getBoolean("gk_${it.key}", it.value))
				it.key to flow
			}.toMap() as MutableMap<String, MutableStateFlow<Boolean>>


	suspend fun reset() {
		getDefaults().forEach {
			setGatekeeper(it.key, it.value)
		}
	}
}