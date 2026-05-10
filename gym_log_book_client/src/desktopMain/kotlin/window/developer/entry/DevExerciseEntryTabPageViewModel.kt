@file:OptIn(ExperimentalUuidApi::class)

package net.michael_bailey.gym_log_book.client.window.developer.entry

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.map
import net.michael_bailey.gym_log_book.client.exercise.service.ExerciseEntryService
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class DevExerciseEntryTabPageViewModel(
	private val exerciseEntryService: ExerciseEntryService,
) : ViewModel() {

	val exerciseEntriesList = exerciseEntryService.allEntries.map { it.toList() }

	fun deleteExerciseEntry(id: Uuid) {

	}

}