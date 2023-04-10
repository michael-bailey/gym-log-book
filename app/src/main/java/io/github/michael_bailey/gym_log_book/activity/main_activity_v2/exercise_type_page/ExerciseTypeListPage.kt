package io.github.michael_bailey.gym_log_book.activity.main_activity_v2

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.michael_bailey.gym_log_book.activity.main_activity_v2.exercise_type_page.ExerciseTypeView

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ExerciseTypeListPage(
	vm: MainActivityV2ViewModel,
	listState: LazyListState
) {

	val state by vm.exerciseTypeListState.observeAsState(initial = listOf())

	Column(
		modifier = Modifier.fillMaxSize(),
		verticalArrangement = Arrangement.SpaceEvenly,
		horizontalAlignment = Alignment.CenterHorizontally
	) {
		LazyColumn(
			Modifier.fillMaxWidth(0.91f),
			state = listState,
			contentPadding = PaddingValues(top = 8.dp, bottom = 8.dp),
			verticalArrangement = Arrangement.spacedBy(8.dp)
		) {
			if (state.isEmpty()) {
				item {
					Column(
						Modifier.fillMaxWidth(),
						verticalArrangement = Arrangement.Center,
						horizontalAlignment = Alignment.CenterHorizontally
					) {
						Text(text = "You don't have an exercise types yet")
						Text(text = "Click the 'add type' button to get started")
					}
				}
			} else {
				items(state) {
					ExerciseTypeView(vm = vm, item = it)
				}
			}
		}
	}
}