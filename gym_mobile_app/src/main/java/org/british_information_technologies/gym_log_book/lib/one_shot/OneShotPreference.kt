package org.british_information_technologies.gym_log_book.lib.one_shot

import android.content.SharedPreferences
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import org.british_information_technologies.gym_log_book.lib.interfaces.IResettable
import javax.inject.Inject

class OneShotPreference @Inject constructor(
	private val name: String,
) : SharedPreferences.OnSharedPreferenceChangeListener, IResettable,
	IOneShotPreference {

	@Inject
	lateinit var preferences: SharedPreferences

	private val coroutineScope = CoroutineScope(Dispatchers.IO)
	private val _isConsumed = MutableStateFlow<Boolean>(true)

	override fun getPreferenceName() = "oneshot_$name"

	override fun isConsumedFlow(): Flow<Boolean> = _isConsumed
	override fun isConsumed(): Boolean = _isConsumed.value
	override fun consume() =
		preferences.edit().putBoolean(getPreferenceName(), true).apply()

	override fun onSharedPreferenceChanged(
		sharedPreferences: SharedPreferences?,
		key: String?
	) {
		when (key) {
			getPreferenceName() -> coroutineScope.launch {
				_isConsumed.emit(sharedPreferences?.getBoolean(key, false) == true)
			}
		}
	}

	override fun reset(): Unit {
		coroutineScope.launch {
			_isConsumed.emit(false)
		}
	}
}
