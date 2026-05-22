@file:OptIn(ExperimentalUuidApi::class, ExperimentalMaterial3Api::class)

package net.michael_bailey.gym_log_book.client.window.developer.entry

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.datetime.TimeZone
import kotlinx.datetime.number
import kotlinx.datetime.toJavaLocalDate
import kotlinx.datetime.toLocalDateTime
import net.michael_bailey.gym_log_book.client.exercise.service.ExerciseEntryService
import net.michael_bailey.gym_log_book.client.exercise.service.ExerciseTypeService
import net.michael_bailey.gym_log_book.shared.exercise.model.ExerciseType
import java.util.*
import kotlin.time.Clock
import kotlin.time.Instant
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class DevExerciseEntryCreateDialogueViewModel(
	private val exerciseTypeService: ExerciseTypeService,
	private val exerciseEntryService: ExerciseEntryService,
) : ViewModel() {
	val exerciseTypes: Flow<List<ExerciseType>> = exerciseTypeService
		.exerciseTypes.map { it.toList() }
	val selectedExerciseType = mutableStateOf<Uuid?>(null)

	val showDatePickerState = mutableStateOf(false)
	val selectedDateState = DatePickerState(
		// todo: make this DI
		locale = Locale.UK,
		initialSelectedDate = Clock.System.now()
			.toLocalDateTime(TimeZone.UTC).date.toJavaLocalDate()
	)
	val formatedDateText = derivedStateOf {
		selectedDateState.selectedDateMillis
			?.let { millis ->
				val date = Instant.fromEpochMilliseconds(millis)
					.toLocalDateTime(TimeZone.currentSystemDefault()).date
				"${date.day}/${date.month.number}/${date.year}"
			} ?: "Select date"
	}

	val weightFieldState = TextFieldState()
	val setFieldState = TextFieldState()
	val repsFieldState = TextFieldState()

	fun submit() = viewModelScope.launch {
		exerciseEntryService.createNewExerciseEntry(
			exerciseTypeId = selectedExerciseType.value!!,
			entryWeight = weightFieldState.text.toString().toDouble(),
			entrySetNumber = setFieldState.text.toString().toInt(),
			entryReps = repsFieldState.text.toString().toInt()
		)
	}

	fun reset() {
		TODO("Not yet implemented")
	}

}