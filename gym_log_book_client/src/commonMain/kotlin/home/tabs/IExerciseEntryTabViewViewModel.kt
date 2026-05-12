@file:OptIn(ExperimentalUuidApi::class)

package net.michael_bailey.gym_log_book.client.home.tabs

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDateTime
import net.michael_bailey.gym_log_book.shared.exercise.model.ExerciseEntry
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

abstract class IExerciseEntryTabViewViewModel : ViewModel() {
	abstract val allEntries: Flow<List<ExerciseEntry>>
	abstract val combinedViewData: Flow<List<ExerciseEntryViewData>>

	data class ExerciseEntryViewData(
		val id: Uuid,
		val date: LocalDateTime,
		var exerciseTypeName: String,
		var setNumber: Int,
		var weight: Double,
		var reps: Int
	)
}