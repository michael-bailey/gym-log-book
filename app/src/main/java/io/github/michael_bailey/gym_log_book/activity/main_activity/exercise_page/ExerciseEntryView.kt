package io.github.michael_bailey.gym_log_book.activity.main_activity.exercise_page

import android.app.Activity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.github.michael_bailey.gym_log_book.activity.main_activity.MainActivityViewModel
import io.github.michael_bailey.gym_log_book.database.entity.EntExerciseEntry
import io.github.michael_bailey.gym_log_book.lib.componenets.CardWithSwipeActions

@Composable
fun ExerciseEntryView(
	modifier: Modifier = Modifier,
	vm: MainActivityViewModel,
	item: EntExerciseEntry
) {
	val activity = LocalContext.current as Activity

	val exerciseTypes by vm.exerciseTypeList.observeAsState(listOf())
	val exerciseName by remember {
		derivedStateOf {
			exerciseTypes.find { it.id == item.exerciseTypeId }?.name ?: "Not found"
		}
	}

	CardWithSwipeActions(
		modifier = modifier,
		actions = {
			Button(onClick = { vm.deleteExerciseEntry(item.id) }) {
				Text("Delete")
			}
			Button(onClick = { vm.amendExerciseEntry(activity, item.id) }) {
				Text("Modify")
			}
		}
	) {
		Column(
			Modifier
				.fillMaxWidth()
				.wrapContentHeight()
				.padding(16.dp)
		) {
			Text(
				text = exerciseName,
				fontSize = 14.sp,
				fontWeight = FontWeight(400)
			)
			Row(
				Modifier
					.wrapContentHeight()
					.fillMaxWidth(),
				horizontalArrangement = Arrangement.SpaceBetween
			) {
				Text(
					text = "Set: ${item.setNumber}",
					fontSize = 22.sp,
					fontWeight = FontWeight(500)
				)
				Text(
					text = "${item.weight} KG",
					fontSize = 22.sp,
					fontWeight = FontWeight(500)
				)
				Text(
					text = "${item.reps} reps",
					fontSize = 22.sp,
					fontWeight = FontWeight(500)
				)
			}
		}
	}
}