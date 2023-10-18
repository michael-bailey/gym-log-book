package org.british_information_technologies.gym_log_book.delegate.impl

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import org.british_information_technologies.gym_log_book.database.entity.EntExerciseType
import org.british_information_technologies.gym_log_book.delegate.IExerciseTypeStateDelegate
import org.british_information_technologies.gym_log_book.repository.ExerciseTypeRepository
import java.util.UUID

class ExerciseTypeStateDelegate(
	private val exerciseTypeRepository: ExerciseTypeRepository,
	private val scope: CoroutineScope = CoroutineScope(Dispatchers.IO)
) : IExerciseTypeStateDelegate {
	override val currentExerciseType: MutableStateFlow<UUID?> =
		MutableStateFlow(null)
	private val exerciseTypesFlow = exerciseTypeRepository.exerciseTypes
	override val exerciseTypes: LiveData<List<EntExerciseType>> =
		exerciseTypesFlow.asLiveData()
	override val exerciseNameMap: LiveData<Map<UUID, String>> =
		exerciseTypesFlow.map {
			it.associate { it.id to it.name }
		}.asLiveData()
	override val selectedExerciseType: LiveData<String?> =
		currentExerciseType.combine(exerciseTypesFlow) { id, list ->
			list.find { item -> item.id == id }?.name
		}.asLiveData()

	override fun setExerciseType(id: UUID?): Job = scope.launch {
		currentExerciseType.emit(id)
	}
}