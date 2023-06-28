package io.github.michael_bailey.gym_log_book.lib.one_shot

import androidx.lifecycle.LiveData

interface IOneShotPreference {
	fun getPreferenceName(): String
	fun data(): LiveData<Boolean>
	fun isConsumed(): Boolean
	fun consume()
}