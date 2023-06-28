package io.github.michael_bailey.gym_log_book.activity.main_activity_v2

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.michael_bailey.gym_log_book.activity.main_activity_v2.dialogue.DeleteExerciseTypeDialogue
import io.github.michael_bailey.gym_log_book.activity.main_activity_v2.exercise_type_page.ExerciseTypeView
import io.github.michael_bailey.gym_log_book.theme.Title

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ExerciseTypeListPage(
	vm: MainActivityV2ViewModel,
	listState: LazyListState
) {

	val removedID by vm.removedID
	val selectedReplacementType by vm.selectedReplacementType

	val state by vm.exerciseTypeList.observeAsState(initial = listOf())
	val exerciseList by vm.exerciseTypeList.observeAsState(initial = listOf())
	val newView by vm.isExerciseTypeDatabaseViewEnabled.observeAsState(false)
	val isEmpty by vm.isExerciseTypeListEmpty.observeAsState(initial = true)
	val typeList by vm.exerciseTypeList.observeAsState(initial = listOf())



	SideEffect { Log.d("Michael", "got newView $newView") }

	val arrangement = remember {
		derivedStateOf {
			if (state.isNotEmpty()) {
				Arrangement.Top
			} else {
				Arrangement.Center
			}
		}
	}

	val typeMap by remember {
		derivedStateOf {
			typeList.map { it.id to it.name }.toMap()
		}
	}

	if (removedID != null) {
		DeleteExerciseTypeDialogue(
			onDisable = vm::clearExerciseTypeDeletion,
			onSelect = vm::setSelectedReplacementType,
			onSubmit = vm::finaliseExerciseRemoval,
			typeList = typeMap,
			removedID = removedID!!,
			selectedType = selectedReplacementType,
		)
	}


	Column(
		modifier = Modifier.fillMaxSize(),
		verticalArrangement = arrangement.value,
		horizontalAlignment = Alignment.CenterHorizontally
	) {
		LazyColumn(
			Modifier.fillMaxWidth(0.91f),
			state = listState,
			contentPadding = PaddingValues(top = 8.dp, bottom = 8.dp),
			verticalArrangement = Arrangement.spacedBy(8.dp)
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
						Text(text = "You Haven't added any exercise types")
						Text(text = "Click the 'Add type' button to add one")
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
						Text("Exercise Types (DB)", fontSize = Title)
					}
				}

				items(exerciseList) {
					ExerciseTypeView(vm = vm, item = it)
				}
			}
		}
	}
}