package io.github.michael_bailey.gym_log_book.lib

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.github.michael_bailey.gym_log_book.extension.application.preferences


class PreferencesManager(
	private val app: Application,
	private val _hasOnboarded: MutableLiveData<Boolean> = MutableLiveData<Boolean>(
		false
	)
		.apply {
			value = app.preferences().getBoolean("hasOnboarded", false)
		},
) {

	val hasOnboarded: LiveData<Boolean> = _hasOnboarded

	init {
		_hasOnboarded.observeForever {
			app.preferences().edit().putBoolean("hasOnboarded", it).apply()
		}
	}

	fun setHasOnboarded(value: Boolean) {
		_hasOnboarded.value = value
	}

}