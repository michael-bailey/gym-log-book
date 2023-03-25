package io.github.michael_bailey.gym_log_book.exercise_set_guide_activity

import android.content.Context
import androidx.lifecycle.ViewModel
import io.github.michael_bailey.gym_log_book.data_type.ExerciseItem
import io.github.michael_bailey.gym_log_book.table.ExerciseTable
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.time.LocalDate

/**
 * Vierw model for
 */
class SetGuideViewModel : ViewModel() {

	private var _exercise = MutableStateFlow("")
	val currentExercise = _exercise.asStateFlow()

	private var _nextWeight = MutableStateFlow(0)
	val nextWeight = _nextWeight.asStateFlow()

	private var _nextReps = MutableStateFlow(0)
	val nextReps = _nextReps.asStateFlow()

	private var _setNumber = MutableStateFlow(1)
	val setNumber = _setNumber.asStateFlow()


	fun setReps(reps: String) {
		if (reps == "") {
			_nextReps.value = 0
		} else {
			_nextReps.value = reps.toInt()
		}
	}

	fun setWeight(weight: String) {
		if (weight == "") {
			_nextWeight.value = 0
		} else {
			_nextWeight.value = weight.toInt()
		}
	}

	fun updateCurrentExercise(exercise: String) {
		_exercise.value = exercise
	}

	fun completeSet() {
		_setNumber.value += 1
		_nextReps.value = 0
	}

	fun finalise(ctx: Context) {
		var table = ExerciseTable(ctx)
		table.appendRow(
			ExerciseItem(
				table.nextId(),
				LocalDate.now(),
				_exercise.value,
				_setNumber.value,
				_nextWeight.value,
				_nextReps.value
			)
		)
	}
}


