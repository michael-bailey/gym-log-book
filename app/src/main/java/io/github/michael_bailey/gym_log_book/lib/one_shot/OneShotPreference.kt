package io.github.michael_bailey.gym_log_book.lib.one_shot

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.github.michael_bailey.gym_log_book.lib.interfaces.IResettable
import javax.inject.Inject

class OneShotPreference @Inject constructor(
	private val name: String,
	private val _isConsumed: MutableLiveData<Boolean> = MutableLiveData<Boolean>(
		false
	),

	) : SharedPreferences.OnSharedPreferenceChangeListener, IResettable,
	IOneShotPreference {

	@Inject
	lateinit var preferences: SharedPreferences

	init {
		_isConsumed.value =
			preferences.getBoolean(getPreferenceName(), false)
	}

	override fun getPreferenceName() = "oneshot_$name"

	override fun data(): LiveData<Boolean> = _isConsumed
	override fun isConsumed(): Boolean = _isConsumed.value!!
	override fun consume() =
		preferences.edit().putBoolean(getPreferenceName(), true).apply()

	override fun onSharedPreferenceChanged(
		sharedPreferences: SharedPreferences?,
		key: String?
	) {
		when (key) {
			getPreferenceName() -> {
				_isConsumed.value = sharedPreferences?.getBoolean(key, false)
			}
		}
	}

	companion object {
		private val store: MutableMap<String, OneShotPreference> = mutableMapOf()
		fun create(name: String): OneShotPreference {
			var oneShot = store[name]
			if (oneShot == null) {
				oneShot = OneShotPreference(name)
				store[name] = oneShot
			}
			return oneShot
		}

		fun getAll(): Map<String, OneShotPreference> = store
	}

	override fun reset() {
		_isConsumed.value = false
		preferences.edit().putBoolean(getPreferenceName(), false).apply()
	}
}
