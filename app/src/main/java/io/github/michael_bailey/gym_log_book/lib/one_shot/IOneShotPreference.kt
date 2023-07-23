package io.github.michael_bailey.gym_log_book.lib.one_shot

import kotlinx.coroutines.flow.Flow

interface IOneShotPreference {
	fun getPreferenceName(): String
	fun isConsumedFlow(): Flow<Boolean>
	fun isConsumed(): Boolean
	fun consume()
}