@file:OptIn(ExperimentalUuidApi::class)

package net.michael_bailey.gym_log_book.client.window.developer.local_entry

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import net.michael_bailey.gym_log_book.client.exercise.service.ILocalExerciseEntryService
import net.michael_bailey.gym_log_book.client.exercise.service.ILocalExerciseTypeService
import net.michael_bailey.gym_log_book.client.sync.SyncStatus
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class DevLocalExerciseEntryTabPageViewModel(
	private val exerciseTypeService: ILocalExerciseTypeService,
	private val exerciseEntryService: ILocalExerciseEntryService,
) : ViewModel() {

	val exerciseEntriesIdList: Flow<List<Uuid>> = exerciseEntryService.allLocalEntryIds
		.onEach { println("got entries $it") }.map { it.toList() }

	val exerciseEntriesList = exerciseEntriesIdList
		.map(::mapIdListToEntry)

	var isSelectionModeShown = mutableStateOf(false)
	val selectedEntryIds = mutableStateListOf<Uuid>()


	private suspend fun mapIdListToEntry(ids: List<Uuid>): List<LocalEntryView> = ids.map {
		exerciseEntryService.getLocalEntry(it).run {
			/// TODO: Move this into the service layer
			LocalEntryView(
				id = id,
				date = date.toLocalDateTime(TimeZone.UTC),
				exerciseTypeId = exerciseTypeId,
				exerciseTypeName = exerciseTypeId.let { exerciseTypeService.getLocalExerciseName(it) ?: "Unknown" },
				setNumber = setNumber,
				weight = weight,
				reps = reps,
				syncStatus = syncStatus,
				syncStatusLabel = syncStatus.let(::getSyncStatusLabel),
				isAlive = isAlive
			)
		}
	}

	private fun getSyncStatusLabel(status: SyncStatus): String = when (status) {
		SyncStatus.SavePending -> "Saving"
		SyncStatus.DeletePending -> "Deleting"
		SyncStatus.Synchronised -> "Synced"
	}

	fun deleteExerciseEntries(selectedEntryIds: List<Uuid>) {
		viewModelScope.launch {
			if (selectedEntryIds.isEmpty()) return@launch

			exerciseEntryService.deleteLocalEntries(selectedEntryIds)
		}
	}

	data class LocalEntryView(
		val id: Uuid,
		val date: LocalDateTime,
		val exerciseTypeId: Uuid,
		val exerciseTypeName: String,
		val setNumber: Int,
		val weight: Double,
		val reps: Int,

		val syncStatusLabel: String,
		val syncStatus: SyncStatus,
		val isAlive: Boolean,
	)


}