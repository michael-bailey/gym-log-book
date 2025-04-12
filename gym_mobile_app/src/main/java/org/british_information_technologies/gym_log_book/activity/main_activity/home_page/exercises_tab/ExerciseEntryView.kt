package org.british_information_technologies.gym_log_book.activity.main_activity.home_page.exercises_tab

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.flow.flow
import org.british_information_technologies.gym_log_book.activity.main_activity.MainActivity
import org.british_information_technologies.gym_log_book.extensions.activity
import org.british_information_technologies.gym_log_book.lib.componenets.CardWithSwipeActions
import org.british_information_technologies.gym_log_book.lib.componenets.scaffold.NavigationButton
import java.time.temporal.ChronoUnit
import java.util.UUID

@Composable
fun ExerciseEntryView(
	modifier: Modifier = Modifier,
	id: UUID
) {
	val activity = activity<MainActivity>()
	val entry by activity.vm.getEntryFlow(id).collectAsState(initial = null)
	val typeFlow by remember {
		derivedStateOf {
			entry?.let { activity.vm.getTypeFlow(it.exerciseTypeId) } ?: flow {}

		}
	}
	val type by typeFlow.collectAsState(initial = null)

	CardWithSwipeActions(
		modifier = modifier,
		actions = {
			Button(onClick = { activity.vm.deleteExerciseEntry(id) }) {
				Text("Delete")
			}
			NavigationButton(
				route = "modify_exercise_dialogue/${id}",
				label = "Modify"
			)
		}
	) {
		Column(
			Modifier
				.fillMaxWidth()
				.wrapContentHeight()
				.padding(16.dp)
		) {
			Row(
				Modifier
					.wrapContentHeight()
					.fillMaxWidth(),
				horizontalArrangement = Arrangement.SpaceBetween
			) {
				Text(
					text = type?.name ?: "...",
					fontSize = 14.sp,
					fontWeight = FontWeight(400)
				)
				Row(
					Modifier
						.wrapContentHeight()
						.wrapContentWidth(),
					horizontalArrangement = Arrangement.spacedBy(10.dp)
				) {
					Text(
						text = "${entry?.createdDate ?: "..."}",
						fontSize = 14.sp,
						fontWeight = FontWeight(400)
					)
					Text(
						text = "${entry?.createdTime?.truncatedTo(ChronoUnit.SECONDS) ?: "..."}",
						fontSize = 14.sp,
						fontWeight = FontWeight(400)
					)
				}
			}
			Row(
				Modifier
					.wrapContentHeight()
					.fillMaxWidth(),
				horizontalArrangement = Arrangement.SpaceBetween
			) {
				Text(
					text = "Set: ${entry?.setNumber ?: "..."}",
					fontSize = 22.sp,
					fontWeight = FontWeight(500)
				)
				Text(
					text = "${entry?.weight ?: "..."} KG",
					fontSize = 22.sp,
					fontWeight = FontWeight(500)
				)
				Text(
					text = "${entry?.reps ?: "..."} reps",
					fontSize = 22.sp,
					fontWeight = FontWeight(500)
				)
			}
		}
	}
}