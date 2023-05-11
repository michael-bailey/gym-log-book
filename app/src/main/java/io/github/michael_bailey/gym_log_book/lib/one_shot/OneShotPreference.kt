package io.github.michael_bailey.gym_log_book.lib.one_shot

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.github.michael_bailey.gym_log_book.app.App
import io.github.michael_bailey.gym_log_book.extension.application.preferences

class OneShotPreference private constructor(
	name: String,
	private val _isConsumed: MutableLiveData<Boolean> = MutableLiveData<Boolean>(
		false
	)
) : SharedPreferences.OnSharedPreferenceChangeListener {

	private val preferenceName: String

	val application: App by lazy { App.getInstance() }

	init {
		preferenceName = "oneshot_$name"
		_isConsumed.value =
			application.preferences().getBoolean(preferenceName, false)
	}

	fun data(): LiveData<Boolean> = _isConsumed
	fun isConsumed(): Boolean = _isConsumed.value!!
	fun consume() =
		application.preferences().edit().putBoolean(preferenceName, true)

	override fun onSharedPreferenceChanged(
		sharedPreferences: SharedPreferences?,
		key: String?
	) {
		when (key) {
			preferenceName -> {
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
	}
}
