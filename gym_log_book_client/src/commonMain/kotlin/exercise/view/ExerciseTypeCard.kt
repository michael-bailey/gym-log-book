@file:OptIn(ExperimentalUuidApi::class)

package net.michael_bailey.gym_log_book.client.exercise.view

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import net.michael_bailey.gym_log_book.client.home.tabs.type.IExerciseTypeTabViewModel.ExerciseTypeViewData
import kotlin.uuid.ExperimentalUuidApi

@Composable
fun ExerciseTypeCard(
	modifier: Modifier = Modifier,
	exerciseType: ExerciseTypeViewData
) {
	Card(
		modifier = modifier
	) {
		ExerciseTypeContent(
			name = exerciseType.name,
			equipmentClass = exerciseType.equipmentClass,
		)
	}
}

@Preview
@Composable
fun ExerciseTypeCard_Preview() {
	val exerciseEntry = ExerciseTypeViewData(
		name = "Type",
		equipmentClass = "class",
	)

	ExerciseTypeCard(
		modifier = Modifier.fillMaxWidth(),
		exerciseType = exerciseEntry
	)
}
