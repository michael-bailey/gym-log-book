@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalUuidApi::class)

package net.michael_bailey.gym_log_book.client.home

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import home.tabs.type.ExerciseTypeTabViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.datetime.TimeZone.Companion.UTC
import kotlinx.datetime.toLocalDateTime
import net.michael_bailey.gym_log_book.client.component.navigation.FabDefinition
import net.michael_bailey.gym_log_book.client.component.navigation.GymAdaptiveScaffold
import net.michael_bailey.gym_log_book.client.di.scopes.AuthenticatedScope
import net.michael_bailey.gym_log_book.client.exercise.service.ExerciseEntryService
import net.michael_bailey.gym_log_book.client.exercise.service.ExerciseTypeService
import net.michael_bailey.gym_log_book.client.home.tabs.HomeTab
import net.michael_bailey.gym_log_book.client.home.tabs.entry.ExerciseEntryTabView
import net.michael_bailey.gym_log_book.client.home.tabs.entry.ExerciseEntryTabViewModel
import net.michael_bailey.gym_log_book.client.home.tabs.entry.IExerciseEntryTabViewModel
import net.michael_bailey.gym_log_book.client.home.tabs.type.ExerciseTypeTabView
import net.michael_bailey.gym_log_book.client.home.tabs.type.IExerciseTypeTabViewModel
import net.michael_bailey.gym_log_book.client.theme.ClientTheme
import net.michael_bailey.gym_log_book.client.util.KoinScope
import net.michael_bailey.gym_log_book.shared.exercise.command.NewExerciseEntryCommand
import net.michael_bailey.gym_log_book.shared.exercise.command.NewExerciseTypeCommand
import net.michael_bailey.gym_log_book.shared.exercise.controller.ExerciseEntryController
import net.michael_bailey.gym_log_book.shared.exercise.controller.ExerciseTypeController
import net.michael_bailey.gym_log_book.shared.exercise.model.EquipmentClass
import net.michael_bailey.gym_log_book.shared.exercise.model.ExerciseEntry
import net.michael_bailey.gym_log_book.shared.exercise.model.ExerciseEntryView
import net.michael_bailey.gym_log_book.shared.exercise.model.ExerciseType
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.scopedOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import kotlin.time.Clock
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@Composable
fun HomePage() {
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
			modifier = Modifier.padding(it),
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
				HomeTab.Types -> ExerciseTypeTabView(
					modifier = Modifier.fillMaxSize(),
				)
			}
		}
	}
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
						)

						override suspend fun newType(command: NewExerciseTypeCommand): ExerciseType = ExerciseType(
							id = typeId,
							name = command.name,
							equipmentClass = command.equipmentClass,
						)

						override suspend fun deleteExerciseTypes(ids: Collection<Uuid>) = Unit
					}
				}

				single<ExerciseEntryController> {
					object : ExerciseEntryController {
						override fun getExerciseEntries(): Flow<Collection<ExerciseEntryView>> = flowOf(
							listOf(
								ExerciseEntryView(
									id = Uuid.random(),
									date = Clock.System.now().toLocalDateTime(UTC),
									exerciseTypeId = typeId,
									exerciseTypeName = "A Type",
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

						override suspend fun newEntry(command: NewExerciseEntryCommand): ExerciseEntry {
							TODO("Not yet implemented")
						}
					}
				}

				singleOf(::ExerciseTypeService)
				singleOf(::ExerciseEntryService)

				scope<AuthenticatedScope> {
					scopedOf(::ExerciseTypeTabViewModel) bind IExerciseTypeTabViewModel::class
					scopedOf(::ExerciseEntryTabViewModel) bind IExerciseEntryTabViewModel::class
				}

			}
		)
	}

	KoinScope<AuthenticatedScope> {
		HomePage()
	}
}
