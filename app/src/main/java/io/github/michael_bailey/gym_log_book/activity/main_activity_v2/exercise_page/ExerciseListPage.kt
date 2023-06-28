package io.github.michael_bailey.gym_log_book.activity.main_activity_v2.exercise_page

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import io.github.michael_bailey.gym_log_book.activity.main_activity_v2.MainActivityV2ViewModel
import io.github.michael_bailey.gym_log_book.theme.StickyHeader
import io.github.michael_bailey.gym_log_book.theme.Title

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun ExerciseListPage(vm: MainActivityV2ViewModel, listState: LazyListState) {

	val exerciseEntryMap by vm.timeExerciseGroupedList.observeAsState(initial = mapOf())
	val isEmpty by vm.isExercisesEmpty.observeAsState(true)

	val arrangement = remember {
		derivedStateOf {
			if (exerciseEntryMap.isNotEmpty()) {
				Arrangement.Top
			} else {
				Arrangement.Center
			}
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
			state = listState,
		) {
			if (isEmpty) {
				item {
					Column(
						Modifier
							.fillMaxWidth()
							.height(250.dp),
						verticalArrangement = Arrangement.Center,
						horizontalAlignment = Alignment.CenterHorizontally
					) {
						Text(text = "You Haven't added any exercise entries")
						Text(text = "Click the 'Add Set' button to add one")
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
						Text("Exercises (DB)", fontSize = Title)
					}
				}

				exerciseEntryMap.forEach {
					stickyHeader {
						Text(
							it.key,
							fontSize = StickyHeader,
							fontWeight = FontWeight(500)
						)
					}

					items(it.value) { item ->
						ExerciseEntryView(vm, item = item)
					}
				}
			}
		}
	}
}
