package org.british_information_technologies.gym_log_book.activity.main_activity.home_page.exercise_types_tab

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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.british_information_technologies.gym_log_book.activity.main_activity.MainActivityViewModel
import org.british_information_technologies.gym_log_book.database.entity.EntExerciseType
import org.british_information_technologies.gym_log_book.lib.componenets.CardWithSwipeActions
import org.british_information_technologies.gym_log_book.lib.componenets.scaffold.NavigationButton

@Composable
fun ExerciseTypeView(
	vm: MainActivityViewModel,
	item: EntExerciseType,
	modifier: Modifier = Modifier
) {
	CardWithSwipeActions(
		modifier = modifier,
		actions = {
			Button(onClick = { vm.initiateExerciseTypeDeletion(item.id) }) {
				Text("Delete")
			}
			NavigationButton(
				route = "modify_exercise_type_dialogue/${item.id}",
				label = "Modify"
			)
		},
		content = {
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
	)

}