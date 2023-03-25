package io.github.michael_bailey.gym_log_book.add_weight_activity

import android.app.Application
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import io.github.michael_bailey.gym_log_book.App
import io.github.michael_bailey.gym_log_book.add_exercise_activity.AddExerciseSetViewModel
import io.github.michael_bailey.gym_log_book.data_type.WeightItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.time.LocalDate

class AddWeightViewModel(application: Application) :
	AndroidViewModel(application) {
	companion object {
		val TAG = "AddWeightViewModel"
	}

	val WeightWatcher = object : TextWatcher {
		override fun beforeTextChanged(
			answer: CharSequence,
			i: Int,
			i1: Int,
			i2: Int
		) {
			Log.i(AddExerciseSetViewModel.TAG, "before text changed ${answer}")
		}

		override fun onTextChanged(answer: CharSequence, i: Int, i1: Int, i2: Int) {
			Log.i(AddExerciseSetViewModel.TAG, "text changed ${answer}")
			if (answer.toString() != "")
				setWeight(answer.toString().toLong())
		}

		override fun afterTextChanged(editable: Editable) {
//			if (editable.toString() === "")
//			editable.set(0,0,"0")
		}
	}

	private val _uiState = MutableStateFlow(AddWeightViewModelState())
	val uiState: StateFlow<AddWeightViewModelState> = _uiState.asStateFlow()

	fun setWeight(weight: Long) {
		_uiState.update {
			it.copy(
				weight = weight,
			)
		}
	}

	fun finalise() {
		var table = getApplication<App>().weightTable
		val v = _uiState.value
		table.appendRow(WeightItem(table.nextId(), LocalDate.now(), v.weight))
	}
}