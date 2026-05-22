@file:OptIn(ExperimentalUuidApi::class)

package net.michael_bailey.gym_log_book.client.home.tabs.entry

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.datetime.TimeZone.Companion.UTC
import kotlinx.datetime.toLocalDateTime
import net.michael_bailey.gym_log_book.client.config.Strings
import net.michael_bailey.gym_log_book.client.exercise.state.ExerciseEntryCreateFormState
import net.michael_bailey.gym_log_book.client.exercise.view.ExerciseEntryCard
import net.michael_bailey.gym_log_book.client.exercise.view.ExerciseEntryCreateFormDialogue
import net.michael_bailey.gym_log_book.client.home.tabs.entry.IExerciseEntryTabViewModel.ExerciseEntryViewData
import net.michael_bailey.gym_log_book.client.theme.ClientTheme
import net.michael_bailey.gym_log_book.client.util.scopedInject
import kotlin.time.Clock
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@Composable
fun ExerciseEntryTabView(
	modifier: Modifier = Modifier,
	viewModel: IExerciseEntryTabViewModel = scopedInject()
) {
	val exerciseEntries by viewModel.combinedViewData.collectAsState(listOf())
	val exerciseTypeState = viewModel.exerciseTypesMap.collectAsState(emptyMap())
	var showCreateEntryFormDialogue by remember { mutableStateOf(false) }

	ExerciseEntryTabView(
		modifier = modifier,
		exerciseEntries = exerciseEntries,
		{ showCreateEntryFormDialogue = true }
	)

	if (showCreateEntryFormDialogue) {
		val state = ExerciseEntryCreateFormState.remembered(
			exerciseTypeState = exerciseTypeState,
		)
		ExerciseEntryCreateFormDialogue(
			formState = state,
			onDismiss = { showCreateEntryFormDialogue = false },
			onSubmit = {
				viewModel.submitCreateEntryForm(
					exerciseType = state.exerciseTypeSelectionState.selectedTypeState.value!!,
					entrySetNumber = state.setNumberTextFieldState.text.toString().toInt(),
					entryWeight = state.weightTextFieldState.text.toString().toDouble(),
					entryReps = state.repsTextFieldState.text.toString().toInt()
				)
				showCreateEntryFormDialogue = false
			},
		)
	}

}

@Composable
fun ExerciseEntryTabView(
	modifier: Modifier,
	exerciseEntries: List<ExerciseEntryViewData>,
	onShowCreateEntryDialogue: () -> Unit
) {
	Box(
		modifier = modifier
			.fillMaxSize(),
		contentAlignment = Alignment.Center,
	) {
		Surface(
			modifier = Modifier.fillMaxSize(),
			shape = MaterialTheme.shapes.extraLarge
		) {
			LazyColumn(
				contentPadding = PaddingValues(24.dp),
				horizontalAlignment = Alignment.CenterHorizontally,
				verticalArrangement = Arrangement.spacedBy(12.dp)
			) {
				item {
					Text(
						text = Strings.HOME_PAGE_EXERCISE_ENTRIES_TITLE,
						fontSize = 32.sp,
						fontWeight = FontWeight(500),
					)
				}

				item {
					Card(
						modifier = Modifier.widthIn(min = 300.dp, max = 500.dp),
						shape = RoundedCornerShape(24.dp)
					) {
						Row(
							modifier = Modifier.padding(12.dp).fillMaxWidth()
						) {
							Button(onClick = onShowCreateEntryDialogue) {
								Text("New...")
							}
						}
					}
				}

				if (exerciseEntries.isEmpty()) {
					item {
						Text(
							text = Strings.EXERCISE_ENTRY_TAB_PLACEHOLDER,
							color = MaterialTheme.colorScheme.onSurfaceVariant,
						)
					}
				} else {
					items(
						items = exerciseEntries
					) {
						ExerciseEntryCard(
							modifier = Modifier.widthIn(min = 300.dp, max = 500.dp),
							it
						)
					}
				}
			}
		}
	}
}

@ExperimentalUuidApi
@Preview
@Composable
fun ExerciseEntryTabView_Preview() {

	val entries = (0 until 10).map {
		val id = Uuid.random()
		ExerciseEntryViewData(
			id = id,
			date = Clock.System.now().toLocalDateTime(UTC),
			exerciseTypeName = "Type Name",
			setNumber = it,
			weight = 100.toDouble(),
			reps = 12
		)
	}

	ClientTheme {
		ExerciseEntryTabView(
			modifier = Modifier.fillMaxSize(),
			exerciseEntries = entries,
			{}
		)
	}
}
