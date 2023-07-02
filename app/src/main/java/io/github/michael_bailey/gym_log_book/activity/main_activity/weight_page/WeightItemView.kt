package io.github.michael_bailey.gym_log_book.activity.main_activity.weight_page

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.github.michael_bailey.gym_log_book.database.entity.EntWeightEntry
import io.github.michael_bailey.gym_log_book.lib.componenets.CardWithSwipeActions
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun WeightItemView(
	item: EntWeightEntry,
	onModify: (UUID) -> Unit,
	onDelete: (UUID) -> Unit,
	modifier: Modifier = Modifier
) {
	CardWithSwipeActions(
		modifier = modifier,
		actions = {
			Button(onClick = { onDelete(item.id) }) {
				Text("Delete")
			}
			Button(onClick = { onModify(item.id) }) {
				Text("Modify")
			}
		},
		content = {
			Column(
				Modifier
					.fillMaxWidth()
					.wrapContentHeight()
					.padding(16.dp),
			) {
				Text(
					text = "${item.value} KG",
					fontSize = 18.sp,
					fontWeight = FontWeight(500)
				)
			}
		}
	)
}