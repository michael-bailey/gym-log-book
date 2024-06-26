package org.british_information_technologies.gym_log_book.activity.main_activity.home_page.exercise_types_tab

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.british_information_technologies.gym_log_book.activity.main_activity.MainActivityViewModel
import org.british_information_technologies.gym_log_book.activity.main_activity.dialogue.DeleteExerciseTypeDialogue
import org.british_information_technologies.gym_log_book.theme.Title

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ExerciseTypeListPage(
	vm: MainActivityViewModel,
) {

	val removedID by vm.removedID
	val selectedReplacementType by vm.selectedReplacementType

	val state by vm.exerciseTypeList.observeAsState(initial = listOf())
	val exerciseList by vm.exerciseTypeList.observeAsState(initial = listOf())
	val isEmpty by vm.isExerciseTypeListEmpty.observeAsState(initial = true)
	val typeList by vm.exerciseTypeList.observeAsState(initial = listOf())

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
			Modifier,
			state = vm.exerciseTypeListState,
			contentPadding = PaddingValues(top = 8.dp, bottom = 8.dp),
			verticalArrangement = Arrangement.spacedBy(8.dp),
			horizontalAlignment = Alignment.CenterHorizontally
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
					ExerciseTypeView(
						modifier = Modifier.fillMaxWidth(0.91f),
						vm = vm,
						item = it
					)
				}
			}
		}
	}
}