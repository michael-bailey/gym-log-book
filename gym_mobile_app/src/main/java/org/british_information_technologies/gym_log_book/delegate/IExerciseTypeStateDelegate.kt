package org.british_information_technologies.gym_log_book.delegate

import androidx.lifecycle.LiveData
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import org.british_information_technologies.gym_log_book.database.entity.EntExerciseType
import java.util.UUID

interface IExerciseTypeStateDelegate {
	val currentExerciseType: MutableStateFlow<UUID?>
	val exerciseTypes: LiveData<List<EntExerciseType>>
	val exerciseNameMap: LiveData<Map<UUID, String>>
	val selectedExerciseType: LiveData<String?>

	fun setExerciseType(id: UUID?): Job
}