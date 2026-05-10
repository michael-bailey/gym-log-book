@file:OptIn(ExperimentalUuidApi::class)

package net.michael_bailey.gym_log_book.client.window.developer.entry

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
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

	val entries by viewModel.exerciseEntriesList.collectAsState(emptyList())

	LazyColumn {
		items(items = entries) { entry ->
			DevExerciseEntryListCard(
				modifier = Modifier.fillMaxWidth(),
				exerciseEntry = entry
			)
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
							(0..100).map {
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