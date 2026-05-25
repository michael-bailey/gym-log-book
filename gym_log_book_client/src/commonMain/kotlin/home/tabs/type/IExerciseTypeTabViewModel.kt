@file:OptIn(ExperimentalUuidApi::class)

package net.michael_bailey.gym_log_book.client.home.tabs.type

import androidx.compose.runtime.State
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

interface IExerciseTypeTabViewModel {
	val typeMap: State<Map<Uuid, ExerciseTypeViewData>>
	val typeList: State<List<ExerciseTypeViewData>>

	data class ExerciseTypeViewData(
		val name: String,
		val equipmentClass: String,
		val isUsingUserWeight: Boolean,
	)
}