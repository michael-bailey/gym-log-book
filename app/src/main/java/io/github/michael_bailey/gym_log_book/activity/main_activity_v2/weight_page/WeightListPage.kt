package io.github.michael_bailey.gym_log_book.activity.main_activity_v2.weight_page

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.github.michael_bailey.gym_log_book.activity.main_activity_v2.MainActivityV2ViewModel
import io.github.michael_bailey.gym_log_book.lib.componenets.CardWithSwipeActions

@Composable
fun WeightListPage(vm: MainActivityV2ViewModel) {
	val state by vm.weightListState.observeAsState(initial = listOf())

	Column(
		modifier = Modifier.fillMaxSize(),
		verticalArrangement = Arrangement.SpaceEvenly,
		horizontalAlignment = Alignment.CenterHorizontally
	) {
		LazyColumn(
			contentPadding = PaddingValues(top = 8.dp, bottom = 8.dp),
			verticalArrangement = Arrangement.spacedBy(8.dp)
		) {
			items(state) { item ->
//				WeightItemView(item)
				CardWithSwipeActions(actions = { Text(text = "Hello world!") }) {
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
		}
	}
}