@file:OptIn(ExperimentalUuidApi::class)

package net.michael_bailey.gym_log_book.client.home.tabs.entry

import androidx.compose.material3.CalendarLocale
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import net.michael_bailey.gym_log_book.client.exercise.service.ExerciseEntryService
import net.michael_bailey.gym_log_book.client.exercise.service.ExerciseTypeService
import kotlin.time.Clock
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class ExerciseEntryTabViewModel(
	private val exerciseEntryService: ExerciseEntryService,
	private val exerciseTypeService: ExerciseTypeService,
	private val clock: Clock,
	private val calendarLocale: CalendarLocale,

) : IExerciseEntryTabViewModel() {

	override val allEntries = exerciseEntryService.allEntries.map {
		it.toList()
	}

	override val combinedViewData = allEntries
	override val exerciseTypesMap: Flow<Map<Uuid, String>> = exerciseTypeService.exerciseNamesMap

	override fun submitCreateEntryForm(
		exerciseType: Uuid,
		entrySetNumber: Int,
		entryWeight: Double,
		entryReps: Int
	) {
		viewModelScope.launch {
			print("submitCreateEntryForm: called")
			exerciseEntryService.createNewExerciseEntry(
				exerciseTypeId = exerciseType,
				entrySetNumber = entrySetNumber,
				entryWeight = entryWeight,
				entryReps = entryReps
			)
		}
	}
}
