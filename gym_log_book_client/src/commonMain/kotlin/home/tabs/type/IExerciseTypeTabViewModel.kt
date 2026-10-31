@file:OptIn(ExperimentalUuidApi::class)

package net.michael_bailey.gym_log_book.client.home.tabs.type

import androidx.compose.runtime.State
import kotlinx.coroutines.flow.StateFlow
import net.michael_bailey.gym_log_book.client.exercise.state.ExerciseTypeCreateFormState
import net.michael_bailey.gym_log_book.shared.exercise.model.EquipmentClass
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

interface IExerciseTypeTabViewModel {
	val typeMapState: State<Map<Uuid, ExerciseTypeViewData>>
	val typeListState: State<List<ExerciseTypeViewData>>

	val isCreateTypeDialogueShown: State<Boolean>
	val createTypeFormState: ExerciseTypeCreateFormState

	val typeMap: StateFlow<Map<Uuid, String>>
	val typeList: StateFlow<List<ExerciseTypeViewData>>

	fun submitCreateTypeForm()

	fun submitCreateTypeForm(
		equipmentClass: EquipmentClass,
		name: String,
	)

	fun showCreateTypeDialogue()
	fun hideCreateTypeDialogue()

	data class ExerciseTypeViewData(
		val id: Uuid,
		val name: String,
		val equipmentClass: String,
	)
}
