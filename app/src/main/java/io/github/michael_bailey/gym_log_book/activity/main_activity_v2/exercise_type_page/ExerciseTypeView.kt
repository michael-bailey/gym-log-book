package io.github.michael_bailey.gym_log_book.activity.main_activity_v2.exercise_type_page

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.github.michael_bailey.gym_log_book.activity.amend_exercise_activity.AmendExerciseActivity
import io.github.michael_bailey.gym_log_book.activity.main_activity_v2.MainActivityV2ViewModel
import io.github.michael_bailey.gym_log_book.data_type.ExerciseType
import io.github.michael_bailey.gym_log_book.lib.componenets.CardWithSwipeActions

@Composable
fun ExerciseTypeView(vm: MainActivityV2ViewModel?, item: ExerciseType) {
	val activity = LocalContext.current as Activity

	val submitIntent = Intent(
		activity.applicationContext,
		AmendExerciseActivity::class.java
	).apply {
		data = Uri.parse("GymLogBook://exercise/${item.id}")
	}

	CardWithSwipeActions(actions = {
		Button(onClick = { vm?.initiateExerciseTypeDeletion(item.id) }) {
			Text("Delete")
		}
	}) {
		Column(
			Modifier
				.fillMaxWidth()
				.wrapContentHeight()
				.padding(16.dp)
		) {
			Text(
				text = item.name,
				fontSize = 22.sp,
				fontWeight = FontWeight(400)
			)
			Row(
				Modifier
					.wrapContentHeight()
					.fillMaxWidth(),
				horizontalArrangement = Arrangement.SpaceBetween
			) {
				if (item.isUsingUserWeight) {
					Text(
						text = "Uses Your Weight",
						fontSize = 14.sp,
						fontWeight = FontWeight(500)
					)
				} else {
					Text(
						text = "Does not use Your Weight",
						fontSize = 14.sp,
						fontWeight = FontWeight(500)
					)
				}
			}
		}
	}
}

@Preview
@Composable
fun ExerciseItemViewPreview() {
	ExerciseTypeView(
		null,
		ExerciseType(1, "Test Exercise", false)

	)
}