@file:OptIn(ExperimentalUuidApi::class)

package net.michael_bailey.gym_log_book.client.home.tabs.entry

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.Flow
import net.michael_bailey.gym_log_book.shared.exercise.model.ExerciseEntryView
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

abstract class IExerciseEntryTabViewModel : ViewModel() {
	abstract val allEntries: Flow<List<ExerciseEntryView>>
	abstract val combinedViewData: Flow<List<ExerciseEntryView>>
	abstract val exerciseTypesMap: Flow<Map<Uuid, String>>

	abstract fun submitCreateEntryForm(
		exerciseType: Uuid,
		entrySetNumber: Int,
		entryWeight: Double,
		entryReps: Int
	)
}
