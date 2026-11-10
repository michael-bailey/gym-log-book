@file:OptIn(ExperimentalUuidApi::class)

package net.michael_bailey.gym_log_book.client.window.developer.local_entry

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import net.michael_bailey.gym_log_book.client.exercise.service.ExerciseTypeService
import net.michael_bailey.gym_log_book.client.exercise.service.ILocalExerciseEntryService
import net.michael_bailey.gym_log_book.client.sync.SyncStatus
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class DevLocalExerciseEntryTabPageViewModel(
	private val exerciseTypeService: ExerciseTypeService,
	private val exerciseEntryService: ILocalExerciseEntryService,
) : ViewModel() {

	val exerciseEntriesIdList: Flow<List<Uuid>> = exerciseEntryService.allLocalEntryIds
		.onEach { println("got entries $it") }.map { it.toList() }

	val exerciseEntriesList = exerciseEntriesIdList
		.map(::mapIdListToEntry)

	var isSelectionModeShown = mutableStateOf(false)
	val selectedEntryIds = mutableStateListOf<Uuid>()

	val isNewEntryModalShown = mutableStateOf(false)

	fun showSelectionMode() {
		isSelectionModeShown.value = true
	}

	fun hideSelectionMode() {
		isSelectionModeShown.value = false
		selectedEntryIds.clear()
	}

	fun toggleExerciseTypeSelection(id: Uuid) {
		if (id in selectedEntryIds) {
			selectedEntryIds.remove(id)
			return
		}
		selectedEntryIds.add(id)
	}

	fun showCreateModal() {
		isNewEntryModalShown.value = true
	}

	fun closeCreateModal() {
		isNewEntryModalShown.value = false
	}

	fun deleteExerciseEntry(id: Uuid) {

	}

	private suspend fun mapIdListToEntry(ids: List<Uuid>): List<LocalEntryView> = ids.map {
		exerciseEntryService.getLocalEntry(it).run {
			/// TODO: Move this into the service layer
			LocalEntryView(
				id = id,
				date = date.toLocalDateTime(TimeZone.UTC),
				exerciseTypeId = exerciseTypeId,
				exerciseTypeName = exerciseTypeId.let { exerciseTypeService.getExerciseName(it) ?: "Unknown" },
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