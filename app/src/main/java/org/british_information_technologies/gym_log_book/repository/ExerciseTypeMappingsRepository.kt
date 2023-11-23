package org.british_information_technologies.gym_log_book.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.UUID
import javax.inject.Inject

class ExerciseTypeMappingsRepository @Inject constructor(
	val exerciseTypeRepository: ExerciseTypeRepository
) {

	val exerciseIdToName: Flow<Map<UUID, String>>
		get() = exerciseTypeRepository.exerciseTypes.map { types ->
			types.associateBy { it.id }.map { it.key to it.value.name }.toMap()
		}
}