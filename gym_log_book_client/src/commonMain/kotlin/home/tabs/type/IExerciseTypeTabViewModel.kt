@file:OptIn(ExperimentalUuidApi::class)

package net.michael_bailey.gym_log_book.client.home.tabs.type

import androidx.compose.runtime.State
import net.michael_bailey.gym_log_book.client.exercise.state.ExerciseTypeCreateFormState
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

interface IExerciseTypeTabViewModel {
	val typeMap: State<Map<Uuid, ExerciseTypeViewData>>
	val typeList: State<List<ExerciseTypeViewData>>

	val isCreateTypeDialogueShown: State<Boolean>
	val createTypeFormState: ExerciseTypeCreateFormState

	fun submitCreateTypeForm()
	fun showCreateTypeDialogue()
	fun hideCreateTypeDialogue()

	data class ExerciseTypeViewData(
		val name: String,
		val equipmentClass: String,
		val isUsingUserWeight: Boolean,
	)
}