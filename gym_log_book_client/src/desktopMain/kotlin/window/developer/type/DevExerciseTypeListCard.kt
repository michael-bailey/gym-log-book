@file:OptIn(ExperimentalUuidApi::class)

package net.michael_bailey.gym_log_book.client.window.developer.type

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@Composable
fun DevExerciseTypeCard(
	id: Uuid,
	name: String,
	typeClass: String,
	showCheckBox: Boolean,
	isChecked: Boolean,
	onCheckedChange: (Uuid) -> Unit,
) {
	Card(
		modifier = Modifier
			.fillMaxWidth()
			.padding(8.dp),
		colors = CardDefaults.cardColors(
			containerColor = if (isChecked) {
				MaterialTheme.colorScheme.secondaryContainer
			} else {
				MaterialTheme.colorScheme.surfaceContainer
			}
		),
	) {
		Row(
			modifier = Modifier
				.fillMaxWidth()
				.clickable(enabled = showCheckBox) { onCheckedChange(id) }
				.padding(12.dp)
		) {
			if (showCheckBox) {
				Checkbox(
					checked = isChecked,
					onCheckedChange = { onCheckedChange(id) }
				)
			}

			Column {
				Text("Id: $id")
				Text("Name: $name")
				Text("Type Class: $typeClass")
			}
		}
	}
}
