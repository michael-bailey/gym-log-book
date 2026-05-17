@file:OptIn(ExperimentalUuidApi::class)

package net.michael_bailey.gym_log_book.client.exercise.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Start
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
		Row(
			modifier = Modifier.padding(12.dp),
			verticalAlignment = Alignment.CenterVertically,
		) {
			Icon(
				modifier = Modifier.padding(end = 12.dp),
				imageVector = Icons.Default.Start,
				contentDescription = ""
			)
			Column {
				Text(
					text = exerciseType.name,
					fontSize = 18.sp
				)
				Text(text = "${exerciseType.equipmentClass} class")
				Text(text = "Uses user weight${exerciseType.isUsingUserWeight}")
			}
		}

	}
}

@Preview
@Composable
fun ExerciseTypeCard_Preview() {
	val exerciseEntry = ExerciseTypeViewData(
		name = "Type",
		equipmentClass = "class",
		isUsingUserWeight = false
	)

	ExerciseTypeCard(
		exerciseType = exerciseEntry
	)
}
