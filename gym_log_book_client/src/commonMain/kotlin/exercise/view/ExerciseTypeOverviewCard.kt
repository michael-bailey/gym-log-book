package net.michael_bailey.gym_log_book.client.exercise.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Category
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import net.michael_bailey.gym_log_book.client.theme.ClientTheme

@Composable
fun ExerciseTypeOverviewCard(
	name: String,
	details: String,
	id: String,
	modifier: Modifier = Modifier,
) {
	Card(
		modifier = modifier.fillMaxWidth(),
		shape = RoundedCornerShape(12.dp),
		colors = CardDefaults.cardColors(
			containerColor = MaterialTheme.colorScheme.surfaceDim,
		),
	) {
		Column(
			modifier = Modifier.padding(8.dp),
		) {
			Row {
				Surface(
					color = MaterialTheme.colorScheme.primaryContainer,
				) {
					Icon(
						modifier = Modifier.size(44.dp),
						imageVector = Icons.Rounded.Category,
						contentDescription = null,
						tint = MaterialTheme.colorScheme.onPrimaryContainer,
					)
				}
				Column {
					Text(
						text = name,
					)
					Text(
						text = details,
						color = MaterialTheme.colorScheme.onSurfaceVariant,
					)
				}
			}

			Text(
				text = id,
				color = MaterialTheme.colorScheme.onSurfaceVariant,
			)
		}
	}
}

@Preview(
	name = "Exercise Type Card",
	showBackground = true,
	widthDp = 420,
)
@Composable
private fun ExerciseTypeOverviewCardPreview() {
	ClientTheme {
		ExerciseTypeOverviewCard(
			name = "Incline Dumbbell Press",
			details = "Free Weight - Tracks external load",
			id = "1d5bbf1a-0d7b-4e3a-8b56-0f9dcff1647c",
		)
	}
}

