package net.michael_bailey.gym_log_book.client.exercise

import home.tabs.type.ExerciseTypeTabViewModel
import net.michael_bailey.gym_log_book.client.application.NetworkManager
import net.michael_bailey.gym_log_book.client.exercise.factory.IExerciseEntryEntityFactory
import net.michael_bailey.gym_log_book.client.exercise.factory.UuidV7ExerciseEntryFactory
import net.michael_bailey.gym_log_book.client.exercise.repository.ExerciseEntryRepository
import net.michael_bailey.gym_log_book.client.exercise.repository.ExerciseTypeRepository
import net.michael_bailey.gym_log_book.client.exercise.service.ExerciseEntryService
import net.michael_bailey.gym_log_book.client.exercise.service.ExerciseTypeService
import net.michael_bailey.gym_log_book.client.home.tabs.entry.ExerciseEntryTabViewModel
import net.michael_bailey.gym_log_book.client.home.tabs.entry.IExerciseEntryTabViewModel
import net.michael_bailey.gym_log_book.client.home.tabs.type.IExerciseTypeTabViewModel
import net.michael_bailey.gym_log_book.client.platform.AppDatabase
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module
import kotlin.time.Clock

val exerciseClientModule = module {

	single {
		UuidV7ExerciseEntryFactory(get<Clock>())
	} bind IExerciseEntryEntityFactory::class

	single {
		val database = get<AppDatabase>()
		database.exerciseEntryDao()
	}

	single {
		val database = get<AppDatabase>()
		database.exerciseTypeDao()
	}

	single {
		val database = get<AppDatabase>()
		database.weightEntryDao()
	}

	single {
		val nm = get<NetworkManager>()
		ExerciseEntryRepository(
			nm.getService(),
			get(),
			get(),
			get(),
			get(),
		)
	}

	single {
		val nm = get<NetworkManager>()
		ExerciseTypeRepository(
			nm.getService(),
			get(),
			get(),
			get(),
		)
	}

	singleOf(::ExerciseEntryService)
	singleOf(::ExerciseTypeService)

	viewModelOf(::ExerciseEntryTabViewModel) bind IExerciseEntryTabViewModel::class
	viewModelOf(::ExerciseTypeTabViewModel) bind IExerciseTypeTabViewModel::class
}
