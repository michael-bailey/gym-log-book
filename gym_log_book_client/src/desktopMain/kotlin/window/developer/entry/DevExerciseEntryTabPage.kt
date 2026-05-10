@file:OptIn(ExperimentalUuidApi::class)

package net.michael_bailey.gym_log_book.client.window.developer.entry

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import net.michael_bailey.gym_log_book.client.exercise.service.ExerciseEntryService
import net.michael_bailey.gym_log_book.client.util.scopedInject
import net.michael_bailey.gym_log_book.shared.exercise.controller.ExerciseEntryController
import net.michael_bailey.gym_log_book.shared.exercise.model.ExerciseEntry
import kotlin.time.Clock
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@Composable
fun DevExerciseEntryTabPage(
	viewModel: DevExerciseEntryTabPageViewModel = scopedInject()
) {

	val entryList by viewModel.exerciseEntriesList.collectAsState(emptyList())

	var isSelectionModeShown by viewModel.isSelectionModeShown
	val selectedEntryIds = viewModel.selectedEntryIds
	val selectedCount = selectedEntryIds.size

	Column(
		modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally
	) {
		Surface(
			modifier = Modifier.fillMaxWidth().padding(8.dp),
			shape = RoundedCornerShape(16.dp),
			color = MaterialTheme.colorScheme.surfaceDim,
		) {
			Row(
				modifier = Modifier.fillMaxWidth(),
				horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterHorizontally),
				verticalAlignment = Alignment.CenterVertically
			) {
				Text(
					text = if (isSelectionModeShown) {
						"$selectedCount selected"
					} else {
						"Entry page"
					}
				)
				if (isSelectionModeShown) {
					OutlinedButton(onClick = viewModel::hideSelectionMode) {
						Text("Cancel")
					}
					Button(
						onClick = { TODO() },
						enabled = selectedCount > 0,
						colors = ButtonDefaults.buttonColors(
							containerColor = MaterialTheme.colorScheme.error,
							contentColor = MaterialTheme.colorScheme.onError,
						),
					) {
						Icon(Icons.Rounded.Delete, contentDescription = null)
						Text("Delete")
					}
				} else {
					OutlinedButton(onClick = viewModel::showSelectionMode) {
						Text("Select")
					}
				}
			}
		}
		LazyColumn(
			modifier = Modifier.fillMaxSize(),
			contentPadding = PaddingValues(8.dp),
			verticalArrangement = Arrangement.spacedBy(8.dp)
		) {
			items(
				items = entryList,
				key = { it.id }
			) { entry ->
				DevExerciseEntryListCard(
					modifier = Modifier.fillMaxWidth(),
					exerciseEntry = entry,
					showCheckBox = isSelectionModeShown,
					isChecked = selectedEntryIds.contains(entry.id),
					onCheckedChange = viewModel::toggleExerciseTypeSelection
				)
			}
		}
	}
}

@Preview
@Composable
fun DevExerciseEntryTabPage_Preview() {
	val viewModel = DevExerciseEntryTabPageViewModel(
		exerciseEntryService = ExerciseEntryService(
			exerciseEntryController = object : ExerciseEntryController {
				override fun getExerciseEntries(): Flow<Collection<ExerciseEntry>> {
					return flow {
						emit(
							(0..10).map {
								ExerciseEntry(
									id = Uuid.random(),
									date = Clock.System.now().toLocalDateTime(TimeZone.UTC),
									exerciseTypeId = Uuid.random(),
									exerciseTypeName = "Random Type",
									setNumber = 3,
									weight = 27.25,
									reps = 12
								)
							}
						)
					}
				}

				override suspend fun createEntry(
					exerciseTypeId: Uuid,
					entrySetNumber: Int,
					entryWeight: Double,
					entryReps: Int
				): ExerciseEntry {
					return ExerciseEntry(
						id = Uuid.random(),
						date = Clock.System.now().toLocalDateTime(TimeZone.UTC),
						exerciseTypeId = Uuid.random(),
						exerciseTypeName = "Random Type",
						setNumber = 3,
						weight = 27.25,
						reps = 12
					)
				}
			}
		)
	)

	DevExerciseEntryTabPage(viewModel)
}