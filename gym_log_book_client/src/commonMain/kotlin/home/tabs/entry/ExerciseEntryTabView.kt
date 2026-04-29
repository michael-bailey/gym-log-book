@file:OptIn(ExperimentalUuidApi::class)

package net.michael_bailey.gym_log_book.client.home.tabs.entry

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.datetime.TimeZone.Companion.UTC
import kotlinx.datetime.toLocalDateTime
import net.michael_bailey.gym_log_book.client.config.Strings
import net.michael_bailey.gym_log_book.client.exercise.view.ExerciseEntryOverviewCard
import net.michael_bailey.gym_log_book.client.home.tabs.IExerciseEntryTabViewViewModel
import net.michael_bailey.gym_log_book.client.theme.ClientTheme
import net.michael_bailey.gym_log_book.shared.exercise.model.ExerciseEntry
import org.koin.compose.viewmodel.koinViewModel
import kotlin.time.Clock
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@Composable
fun ExerciseEntryTabView(
	modifier: Modifier = Modifier,
	viewModel: IExerciseEntryTabViewViewModel = koinViewModel()
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

			val exerciseEntries by viewModel.combinedViewData.collectAsState(listOf())

			LazyColumn(
				contentPadding = PaddingValues(24.dp),
				horizontalAlignment = Alignment.CenterHorizontally
			) {
				item {
					Text(
						text = Strings.HOME_PAGE_EXERCISE_ENTRIES_TITLE,
						fontSize = 32.sp,
						fontWeight = FontWeight(500),
					)
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
						ExerciseEntryOverviewCard(
							modifier = Modifier.fillMaxWidth().padding(8.dp),
							id = it.id.toString(),
							name = it.exerciseTypeName,
							date = it.date
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

	val viewModel = object : IExerciseEntryTabViewViewModel() {
		override val allEntries: Flow<List<ExerciseEntry>>
			get() = flow {}
		override val combinedViewData: Flow<List<ExerciseEntryViewData>>
			get() = flow {
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

				emit(entries)
			}

	}

	ClientTheme {
		ExerciseEntryTabView(
			viewModel = viewModel
		)
	}
}