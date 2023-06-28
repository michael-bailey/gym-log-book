package io.github.michael_bailey.gym_log_book.activity.main_activity_v2.exercise_type_page

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.github.michael_bailey.gym_log_book.activity.main_activity_v2.MainActivityV2ViewModel
import io.github.michael_bailey.gym_log_book.database.entity.EntExerciseType
import io.github.michael_bailey.gym_log_book.lib.componenets.CardWithSwipeActions

@Composable
fun ExerciseTypeView(vm: MainActivityV2ViewModel?, item: EntExerciseType) {
	val activity = LocalContext.current as Activity

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
				if (item.usesUserWeight) {
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