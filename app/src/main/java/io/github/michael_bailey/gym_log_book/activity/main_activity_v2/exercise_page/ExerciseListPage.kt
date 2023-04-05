package io.github.michael_bailey.gym_log_book.activity.main_activity_v2.exercise_page

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.github.michael_bailey.gym_log_book.activity.main_activity_v2.MainActivityV2ViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun ExerciseListPage(vm: MainActivityV2ViewModel, listState: LazyListState) {

	val state by vm.exerciseListState.observeAsState(initial = listOf())
	val groups = remember(state) {
		state.reversed()
			.groupBy { it.date }
			.toList()
			.sortedByDescending { it.first }
	}

	LazyColumn(
		Modifier.fillMaxWidth(0.91f),
		state = listState,
		contentPadding = PaddingValues(top = 8.dp, bottom = 8.dp),
		verticalArrangement = Arrangement.spacedBy(8.dp)
	) {
		item {
			Column(
				Modifier
					.fillMaxWidth()
					.height(250.dp),
				verticalArrangement = Arrangement.Center,
				horizontalAlignment = Alignment.CenterHorizontally
			) {
				Text("Hello There!", fontSize = 32.sp)
			}
		}

		groups.forEach {
			stickyHeader {
				Text("${it.first}", fontSize = 24.sp, fontWeight = FontWeight(500))
			}
			items(it.second) { item ->
				ExerciseItemView(vm, item = item)
			}
		}
	}
}
