package io.github.michael_bailey.gym_log_book.delegate

import androidx.lifecycle.LiveData
import io.github.michael_bailey.gym_log_book.database.entity.EntExerciseType
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import java.util.UUID

interface IExerciseTypeStateDelegate {
	val currentExerciseType: MutableStateFlow<UUID?>
	val exerciseTypes: LiveData<List<EntExerciseType>>
	val exerciseNameMap: LiveData<Map<UUID, String>>
	val selectedExerciseType: LiveData<String?>

	fun setExerciseType(id: UUID?): Job
}