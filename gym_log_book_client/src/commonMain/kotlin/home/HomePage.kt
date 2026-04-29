@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalUuidApi::class)

package net.michael_bailey.gym_log_book.client.home

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.datetime.TimeZone.Companion.UTC
import kotlinx.datetime.toLocalDateTime
import net.michael_bailey.gym_log_book.client.component.navigation.FabDefinition
import net.michael_bailey.gym_log_book.client.component.navigation.GymAdaptiveScaffold
import net.michael_bailey.gym_log_book.client.exercise.service.ExerciseEntryService
import net.michael_bailey.gym_log_book.client.exercise.service.ExerciseTypeService
import net.michael_bailey.gym_log_book.client.exercise.view_model.ExerciseTypeListViewModel
import net.michael_bailey.gym_log_book.client.home.HomePageViewModel.HomePageTab
import net.michael_bailey.gym_log_book.client.home.tabs.HomeTab
import net.michael_bailey.gym_log_book.client.home.tabs.IExerciseEntryTabViewViewModel
import net.michael_bailey.gym_log_book.client.home.tabs.entry.ExerciseEntryTabView
import net.michael_bailey.gym_log_book.client.home.tabs.entry.ExerciseEntryTabViewViewModel
import net.michael_bailey.gym_log_book.client.home.tabs.type.ExerciseTypeOverviewList
import net.michael_bailey.gym_log_book.client.theme.ClientTheme
import net.michael_bailey.gym_log_book.shared.exercise.controller.ExerciseEntryController
import net.michael_bailey.gym_log_book.shared.exercise.controller.ExerciseTypeController
import net.michael_bailey.gym_log_book.shared.exercise.model.EquipmentClass
import net.michael_bailey.gym_log_book.shared.exercise.model.ExerciseEntry
import net.michael_bailey.gym_log_book.shared.exercise.model.ExerciseType
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module
import kotlin.time.Clock
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@Composable
fun HomePage(
	vm: HomePageViewModel = koinViewModel()
) {
	ClientTheme {
		NewHomePage()
	}
}

@Composable
fun NewHomePage() {

	var currentTabs by remember { mutableStateOf(HomeTab.Exercises) }

	GymAdaptiveScaffold(
		currentRoute = currentTabs,
		onNavigate = { currentTabs = it },
		fab = FabDefinition(
			icon = Icons.Rounded.Add,
			onClick = { println("Not implemented") }
		),
		navContent = {
			HomeTab.entries.forEach {
				item(
					icon = it.icon,
					label = it.label,
					route = it
				)
			}
		}
	) {
		HomeContentSurface(
			modifier = Modifier.padding(),
			currentTabs = currentTabs,
		)
	}
}

@Composable
private fun HomeContentSurface(
	modifier: Modifier = Modifier,
	currentTabs: HomeTab,
) {
	Box(
		modifier = modifier,
	) {
		AnimatedContent(
			modifier = Modifier.fillMaxSize(),
			targetState = currentTabs,
		) { destination ->
			when (destination) {
				HomeTab.Exercises -> ExerciseEntryTabView(
					modifier = Modifier.fillMaxSize(),
				)

				HomeTab.Types -> ExerciseTypeOverviewList(
					modifier = Modifier.fillMaxSize(),
				)
			}
		}
	}
}

private fun navigationIconFor(
	tab: HomePageTab,
): ImageVector = when (tab) {
	HomePageTab.ExerciseEntryTab -> Icons.Rounded.FitnessCenter
	HomePageTab.ExerciseTypeTab -> Icons.Rounded.Category
}

private fun fabIconFor(
	tab: HomePageTab,
): ImageVector = when (tab) {
	HomePageTab.ExerciseEntryTab -> Icons.Rounded.PlayArrow
	HomePageTab.ExerciseTypeTab -> Icons.Rounded.AddCircle
}

@Preview
@Composable
fun HomePage_Preview() {

	val typeId = Uuid.random()

	startKoin {
		modules(
			module {

				single<ExerciseTypeController> {
					object : ExerciseTypeController {
						override fun exerciseTypes(): Flow<Collection<ExerciseType>> = flowOf(
							listOf(
								ExerciseType(
									id = typeId,
									name = "Text Type",
									equipmentClass = EquipmentClass.Machine,
									isUsingUserWeight = false
								)
							)
						)

						override suspend fun createExerciseType(
							name: String,
							equipmentClass: EquipmentClass,
						): ExerciseType = ExerciseType(
							id = typeId,
							name = name,
							equipmentClass = equipmentClass,
							isUsingUserWeight = equipmentClass == EquipmentClass.UsesUserWeight
						)

						override suspend fun deleteExerciseTypes(ids: Collection<Uuid>) = Unit
					}
				}

				single<ExerciseEntryController> {
					object : ExerciseEntryController {
						override fun getExerciseEntries(): Flow<Collection<ExerciseEntry>> = flowOf(
							listOf(
								ExerciseEntry(
									id = Uuid.random(),
									date = Clock.System.now().toLocalDateTime(UTC),
									exerciseTypeId = typeId,
									setNumber = 1,
									weight = 1.0,
									reps = 1
								)
							)
						)

						override suspend fun createEntry(
							exerciseTypeId: Uuid,
							entrySetNumber: Int,
							entryWeight: Double,
							entryReps: Int
						): ExerciseEntry {
							TODO("Not yet implemented")
						}
					}
				}

				singleOf(::ExerciseTypeService)
				singleOf(::ExerciseEntryService)

				viewModelOf(::ExerciseTypeListViewModel)

				viewModelOf(::ExerciseEntryTabViewViewModel) bind IExerciseEntryTabViewViewModel::class

			}
		)
	}

	val viewModel = HomePageViewModel()

	HomePage(viewModel)
}
