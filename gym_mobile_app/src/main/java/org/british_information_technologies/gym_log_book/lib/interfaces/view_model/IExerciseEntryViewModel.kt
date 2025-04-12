package org.british_information_technologies.gym_log_book.lib.interfaces.view_model

import androidx.lifecycle.LiveData
import kotlinx.coroutines.Job
import org.british_information_technologies.gym_log_book.database.entity.EntExerciseEntry
import java.util.UUID

interface IExerciseEntryViewModel {

	val exerciseNameMap: LiveData<Map<UUID, String>>
	val exerciseEntryList: LiveData<List<EntExerciseEntry>>

	fun modifyExerciseEntry(
		newEntity: EntExerciseEntry
	): Job

	fun deleteExerciseEntry(
		exerciseID: UUID
	): Job
}
