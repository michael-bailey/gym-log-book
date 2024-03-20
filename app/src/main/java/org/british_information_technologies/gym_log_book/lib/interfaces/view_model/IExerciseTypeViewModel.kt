package org.british_information_technologies.gym_log_book.lib.interfaces.view_model

import androidx.compose.runtime.MutableState
import androidx.lifecycle.LiveData
import org.british_information_technologies.gym_log_book.database.entity.EntExerciseType

interface IExerciseTypeViewModel {

	val exerciseName: LiveData<String>
	val isUsingUserWeight: LiveData<Boolean>
	val canSubmit: LiveData<Boolean>

	fun createNewType(
		exerciseName: String,
		usingUserWeight: MutableState<Boolean>
	)

	fun update(
		exerciseType: EntExerciseType
	)
}
