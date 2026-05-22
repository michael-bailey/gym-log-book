@file:OptIn(ExperimentalUuidApi::class)

package net.michael_bailey.gym_log_book.client.home.tabs.entry

import androidx.compose.material3.CalendarLocale
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import net.michael_bailey.gym_log_book.client.exercise.service.ExerciseEntryService
import net.michael_bailey.gym_log_book.client.exercise.service.ExerciseTypeService
import net.michael_bailey.gym_log_book.shared.exercise.model.ExerciseEntry
import net.michael_bailey.gym_log_book.shared.exercise.model.ExerciseType
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

	override val combinedViewData = combine(
		exerciseTypeService.exerciseTypes,
		allEntries,
		::combineMapTypeWIthEntry
	)
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

	private fun combineMapTypeWIthEntry(
		types: Collection<ExerciseType>,
		entries: Collection<ExerciseEntry>,
	): List<ExerciseEntryViewData> = types
		.associateBy { it.id }
		.let(genMapEntriesWithTypes(entries))

	private fun genMapEntriesWithTypes(
		entries: Collection<ExerciseEntry>
	): (Map<Uuid, ExerciseType>) -> List<ExerciseEntryViewData> = { types ->
		mapEntriesWithTypes(entries, types)
	}

	private fun mapEntriesWithTypes(
		entries: Collection<ExerciseEntry>,
		types: Map<Uuid, ExerciseType>
	): List<ExerciseEntryViewData> =
		entries.map(genMapEntryToViewData(types))

	private fun genMapEntryToViewData(
		types: Map<Uuid, ExerciseType>,
	): (ExerciseEntry) -> ExerciseEntryViewData = {
		mapEntryToViewData(it, types)
	}

	private fun mapEntryToViewData(
		entry: ExerciseEntry,
		types: Map<Uuid, ExerciseType>
	): ExerciseEntryViewData = ExerciseEntryViewData(
		id = entry.id,
		date = entry.date,
		exerciseTypeName = types[entry.exerciseTypeId]?.name ?: "Not Found",
		setNumber = entry.setNumber,
		weight = entry.weight,
		reps = entry.reps
	)

}