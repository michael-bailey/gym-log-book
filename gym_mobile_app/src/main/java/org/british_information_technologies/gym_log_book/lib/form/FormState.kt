package org.british_information_technologies.gym_log_book.lib.form

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf

class FormState {

	private val stateMap = mutableStateMapOf<String, MutableState<Any>>()

	fun <S : Any> getState(name: String, default: S): State<S> {
		var state = stateMap[name]

		if (state == null) {
			state = mutableStateOf(default)
			stateMap[name] = state
		}
		return state as State<S>
	}

	fun <S : Any> setState(name: String, value: S) {
		val state = stateMap[name]
		if (state == null) {
			stateMap[name] = mutableStateOf(value)
		} else {
			state.value = value
		}
	}

}
