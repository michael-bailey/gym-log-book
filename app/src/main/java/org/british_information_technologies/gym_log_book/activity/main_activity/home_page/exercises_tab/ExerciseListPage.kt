package org.british_information_technologies.gym_log_book.activity.main_activity.home_page.exercises_tab

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.google.android.material.elevation.SurfaceColors
import org.british_information_technologies.gym_log_book.activity.main_activity.MainActivity
import org.british_information_technologies.gym_log_book.extension.activity
import org.british_information_technologies.gym_log_book.lib.interfaces.view_model.IExerciseEntryListViewModel
import org.british_information_technologies.gym_log_book.theme.StickyHeader
import org.british_information_technologies.gym_log_book.theme.Title

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ExerciseListPage(vm: IExerciseEntryListViewModel) {
	val activity = activity<MainActivity>()
	val listState = vm.exerciseListState
	val exerciseEntryMapState =
		vm.timeExerciseGroupedList.observeAsState(initial = mapOf())
	val exerciseEntryMap by exerciseEntryMapState
	val isEmpty by vm.isExercisesEmpty.observeAsState(true)
	val currentHeaderState = remember { mutableStateOf<String?>(null) }

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
			contentPadding = PaddingValues(top = 8.dp, bottom = 8.dp),
			verticalArrangement = Arrangement.spacedBy(8.dp),
			horizontalAlignment = Alignment.CenterHorizontally,
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
						Text("Exercises (NEW APP)", fontSize = Title)
					}
				}

				exerciseEntryMap.forEach { entry ->
					stickyHeader {
						Surface(
							modifier = Modifier
								.fillParentMaxWidth()
								.onGloballyPositioned {
									if (it.positionInParent().y <= 0f) {
										currentHeaderState.value = entry.key
									}
								},
							color = (if (entry.key == currentHeaderState.value && listState.firstVisibleItemIndex > 0) Color(
								SurfaceColors.SURFACE_2.getColor(LocalContext.current)
							) else MaterialTheme.colorScheme.background)
						) {
							Text(
								modifier = Modifier.padding(18.dp, 8.dp),
								text = entry.key,
								fontSize = StickyHeader,
								fontWeight = FontWeight(400)
							)
						}
					}
					// change this query to be just ids
					items(entry.value) { item ->
						ExerciseEntryView(Modifier.fillMaxWidth(0.91F), id = item)
					}
				}
			}
		}
	}
}
