package io.github.michael_bailey.gym_log_book.view_model

import android.app.Application
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import io.github.michael_bailey.gym_log_book.App
import io.github.michael_bailey.gym_log_book.data_type.ExerciseItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.time.LocalDate

class AddExerciseSetViewModel(application: Application) :
	AndroidViewModel(application) {
	companion object {
		val TAG = "AddExerciseSetViewModel"
	}

	// text watchers, used to update state
	val ExerciseWatcher = object : TextWatcher {
		override fun beforeTextChanged(
			charSequence: CharSequence,
			i: Int,
			i1: Int,
			i2: Int
		) {
		}

		override fun onTextChanged(answer: CharSequence, i: Int, i1: Int, i2: Int) {
			setExercise(answer.toString())
		}

		override fun afterTextChanged(editable: Editable) {}
	}
	val WeightWatcher = object : TextWatcher {
		override fun beforeTextChanged(
			answer: CharSequence,
			i: Int,
			i1: Int,
			i2: Int
		) {
			Log.i(TAG, "before text changed ${answer}")
		}

		override fun onTextChanged(answer: CharSequence, i: Int, i1: Int, i2: Int) {
			Log.i(TAG, "text changed ${answer}")
			if (answer.toString() != "")
				setWeight(answer.toString().toLong())
		}

		override fun afterTextChanged(editable: Editable) {
//			if (editable.toString() === "")
//			editable.set(0,0,"0")
		}
	}
	val SetNumberWatcher = object : TextWatcher {
		override fun beforeTextChanged(
			charSequence: CharSequence,
			i: Int,
			i1: Int,
			i2: Int
		) {
		}

		override fun onTextChanged(answer: CharSequence, i: Int, i1: Int, i2: Int) {
			Log.i(TAG, "text changed ${answer}")
			if (answer.toString() != "")
				setSetNumber(answer.toString().toLong())
		}

		override fun afterTextChanged(editable: Editable) {}
	}
	val RepsWatcher = object : TextWatcher {
		override fun beforeTextChanged(
			charSequence: CharSequence,
			i: Int,
			i1: Int,
			i2: Int
		) {
		}

		override fun onTextChanged(answer: CharSequence, i: Int, i1: Int, i2: Int) {
			Log.i(TAG, "text changed ${answer}")
			if (answer.toString() != "")
				setReps(answer.toString().toLong())
		}

		override fun afterTextChanged(editable: Editable) {}
	}

	// Expose screen UI state
	private val _uiState = MutableStateFlow(AddExerciseSetViewModelState())
	val uiState: StateFlow<AddExerciseSetViewModelState> = _uiState.asStateFlow()

	/// sets the exercise type
	fun setExercise(exercise: String) {
		_uiState.update {
			it.copy(
				exercise = exercise,
			)
		}
	}

	/// Sets the set number
	fun setSetNumber(set: Long) {
		_uiState.update {
			it.copy(
				setNumber = set,
			)
		}
	}

	/// Sets the weight number
	fun setWeight(weight: Long) {
		_uiState.update {
			it.copy(
				weight = weight,
			)
		}
	}

	/// sets the amount of reps
	fun setReps(reps: Long) {
		_uiState.update {
			it.copy(
				weight = reps,
			)
		}
	}

	fun finalise() {
		var table = getApplication<App>().exerciseTable
		val v = _uiState.value
		table.appendRow(
			ExerciseItem(
				table.nextId(),
				LocalDate.now(),
				v.exercise,
				v.setNumber,
				v.weight,
				v.reps
			)
		)
	}
}