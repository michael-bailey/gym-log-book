package net.michael_bailey.gym_log_book.client.exercise.view

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Start
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ExerciseTypeContent(
	modifier: Modifier = Modifier,
	name: String,
	equipmentClass: String,
) {
	Column(
		modifier = modifier
	) {
		Row(
			modifier = Modifier.fillMaxWidth()
				.wrapContentHeight()
				.padding(16.dp),
			verticalAlignment = Alignment.CenterVertically,
		) {
			Icon(
				modifier = Modifier.padding(end = 12.dp),
				imageVector = Icons.Default.Start,
				contentDescription = ""
			)
			Column {
				Text(
					text = name,
					fontSize = 18.sp
				)
				Text(text = "$equipmentClass class")
			}
		}
	}
}

@Preview
@Composable
fun ExerciseTypeContent_Preview() {
	ExerciseTypeContent(
		modifier = Modifier.fillMaxWidth(),
		name = "Type",
		equipmentClass = "Free weight",
	)
}
