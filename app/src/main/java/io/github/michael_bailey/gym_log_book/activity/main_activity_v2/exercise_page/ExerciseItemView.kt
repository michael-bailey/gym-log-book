package io.github.michael_bailey.gym_log_book.activity.main_activity_v2.exercise_page

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.github.michael_bailey.gym_log_book.activity.amend_exercise_activity.AmendExerciseActivity
import io.github.michael_bailey.gym_log_book.activity.amend_exercise_activity.AmendExerciseActivityIntentUtils
import io.github.michael_bailey.gym_log_book.activity.main_activity_v2.MainActivityV2ViewModel
import io.github.michael_bailey.gym_log_book.data_type.ExerciseItem
import io.github.michael_bailey.gym_log_book.lib.componenets.CardWithSwipeActions

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun ExerciseItemView(vm: MainActivityV2ViewModel, item: ExerciseItem) {
	val activity = LocalContext.current as Activity

	val submitIntent = Intent(
		activity.applicationContext,
		AmendExerciseActivity::class.java
	).apply {
		data = Uri.parse("GymLogBook://exercise/${item.id}")
	}

	CardWithSwipeActions(actions = {
		Button(onClick = { vm.deleteExercise(item.id) }) {
			Text("Delete")
		}
		Button(onClick = {
			AmendExerciseActivityIntentUtils.startAmendActivity(
				activity,
				item.id
			)
		}) {
			Text("Modify")
		}
	}) {
		Column(
			Modifier
				.fillMaxWidth()
				.wrapContentHeight()
				.padding(16.dp)
		) {
			Text(
				text = "${item.exercise}",
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