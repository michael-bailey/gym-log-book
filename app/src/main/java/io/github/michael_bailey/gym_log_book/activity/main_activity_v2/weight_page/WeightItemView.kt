package io.github.michael_bailey.gym_log_book.activity.main_activity_v2.weight_page

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.github.michael_bailey.gym_log_book.data_type.WeightItem
import io.github.michael_bailey.gym_log_book.lib.componenets.CardWithSwipeActions

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun WeightItemView(item: WeightItem) {
	CardWithSwipeActions(actions = {
		Button(onClick = { /*TODO*/ }) {
			Text("Delete")
		}
		Button(onClick = { /*TODO*/ }) {
			Text("Modify")
		}
	}) {
		Column(
			Modifier
				.fillMaxSize(0.9f)
				.padding(16.dp)
		) {
			Text(
				text = "${item.weight} KG",
				fontSize = 18.sp,
				fontWeight = FontWeight(500)
			)
		}
	}
}