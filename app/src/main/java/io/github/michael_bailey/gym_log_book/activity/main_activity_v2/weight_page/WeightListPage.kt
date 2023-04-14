package io.github.michael_bailey.gym_log_book.activity.main_activity_v2.weight_page

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.michael_bailey.gym_log_book.activity.main_activity_v2.MainActivityV2ViewModel
import io.github.michael_bailey.gym_log_book.theme.Title

@Composable
fun WeightListPage(vm: MainActivityV2ViewModel, listState: LazyListState) {
	val state by vm.weightListState.observeAsState(initial = listOf())


	val arrangement = derivedStateOf {
		if (state.isNotEmpty()) {
			Arrangement.Top
		} else {
			Arrangement.Center
		}
	}

	Column(
		modifier = Modifier.fillMaxSize(),
		verticalArrangement = arrangement.value,
		horizontalAlignment = Alignment.CenterHorizontally
	) {

		LazyColumn(
			Modifier.fillMaxWidth(0.91f),
			contentPadding = PaddingValues(top = 8.dp, bottom = 8.dp),
			verticalArrangement = Arrangement.spacedBy(8.dp),
			state = listState
		) {
			if (state.isEmpty()) {
				item {
					Column(
						Modifier
							.fillMaxHeight()
							.height(250.dp),
						verticalArrangement = Arrangement.Center,
						horizontalAlignment = Alignment.CenterHorizontally
					) {
						Text(text = "You Haven't added any Weights")
						Text(text = "Click the 'add Weight' button to add one")
					}
				}
			} else {

				item {
					Column(
						Modifier
							.fillMaxWidth()
							.height(250.dp),
						verticalArrangement = Arrangement.Center,
						horizontalAlignment = Alignment.CenterHorizontally
					) {
						Text("Weights", fontSize = Title)
					}
				}

				items(state) { item ->
					WeightItemView(item)
				}
			}
		}
	}
}