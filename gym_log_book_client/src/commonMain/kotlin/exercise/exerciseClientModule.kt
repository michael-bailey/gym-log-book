package net.michael_bailey.gym_log_book.client.exercise

import kotlinx.rpc.RpcClient
import kotlinx.rpc.withService
import net.michael_bailey.gym_log_book.client.di.scopes.AuthenticatedScope
import net.michael_bailey.gym_log_book.client.exercise.service.ExerciseEntryService
import net.michael_bailey.gym_log_book.client.exercise.service.ExerciseTypeService
import net.michael_bailey.gym_log_book.client.exercise.view_model.ExerciseTypeListViewModel
import net.michael_bailey.gym_log_book.client.home.tabs.IExerciseEntryTabViewViewModel
import net.michael_bailey.gym_log_book.client.home.tabs.entry.ExerciseEntryTabViewViewModel
import net.michael_bailey.gym_log_book.shared.exercise.controller.ExerciseEntryController
import net.michael_bailey.gym_log_book.shared.exercise.controller.ExerciseTypeController
import org.koin.core.module.dsl.scopedOf
import org.koin.dsl.bind
import org.koin.dsl.module

val exerciseClientModule = module {

	scope<AuthenticatedScope> {
		scoped {
			println("creating exercise RCP")
			get<RpcClient>().withService<ExerciseTypeController>()
		} bind ExerciseTypeController::class

		scoped {
			println("creating exercise RCP")
			get<RpcClient>().withService<ExerciseEntryController>()
		} bind ExerciseEntryController::class

		scopedOf(::ExerciseEntryService)
		scopedOf(::ExerciseTypeService)

		scopedOf(::ExerciseTypeListViewModel)
		scopedOf(::ExerciseEntryTabViewViewModel) bind IExerciseEntryTabViewViewModel::class
	}
}
