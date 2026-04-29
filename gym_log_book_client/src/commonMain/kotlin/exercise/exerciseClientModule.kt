package net.michael_bailey.gym_log_book.client.exercise

import kotlinx.rpc.RpcClient
import kotlinx.rpc.withService
import net.michael_bailey.gym_log_book.client.exercise.service.ExerciseEntryService
import net.michael_bailey.gym_log_book.client.exercise.service.ExerciseTypeService
import net.michael_bailey.gym_log_book.client.exercise.view_model.ExerciseTypeListViewModel
import net.michael_bailey.gym_log_book.client.home.HomePageViewModel
import net.michael_bailey.gym_log_book.client.home.tabs.IExerciseEntryTabViewViewModel
import net.michael_bailey.gym_log_book.client.home.tabs.entry.ExerciseEntryTabViewViewModel
import net.michael_bailey.gym_log_book.shared.exercise.controller.ExerciseEntryController
import net.michael_bailey.gym_log_book.shared.exercise.controller.ExerciseTypeController
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val exerciseClientModule = module {
	single {
		println("creating exercise RCP")
		get<RpcClient>().withService<ExerciseTypeController>()
	} bind ExerciseTypeController::class

	single {
		println("creating exercise RCP")
		get<RpcClient>().withService<ExerciseEntryController>()
	} bind ExerciseEntryController::class

	factoryOf(::ExerciseEntryService)

	viewModelOf(::ExerciseTypeListViewModel)
	viewModelOf(::ExerciseEntryTabViewViewModel) bind IExerciseEntryTabViewViewModel::class

	viewModelOf(::HomePageViewModel)

	factoryOf(::ExerciseTypeService)

}