package io.github.michael_bailey.gym_log_book.activity.main_activity_v2.weight_page

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.michael_bailey.gym_log_book.activity.main_activity_v2.MainActivityV2ViewModel

@Composable
fun WeightListPage(vm: MainActivityV2ViewModel, listState: LazyListState) {
	val state by vm.weightListState.observeAsState(initial = listOf())

	Column(
		modifier = Modifier.fillMaxSize(),
		verticalArrangement = Arrangement.Top,
		horizontalAlignment = Alignment.CenterHorizontally
	) {
		LazyColumn(
			contentPadding = PaddingValues(top = 8.dp, bottom = 8.dp),
			verticalArrangement = Arrangement.spacedBy(8.dp),
			state = listState
		) {
			items(state) { item ->
				WeightItemView(item)
			}
		}
	}
}