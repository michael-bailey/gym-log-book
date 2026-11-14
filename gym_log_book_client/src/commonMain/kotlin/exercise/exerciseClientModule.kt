package net.michael_bailey.gym_log_book.client.exercise

import home.tabs.type.ExerciseTypeTabViewModel
import net.michael_bailey.gym_log_book.client.application.NetworkManager
import net.michael_bailey.gym_log_book.client.exercise.factory.IExerciseEntryEntityFactory
import net.michael_bailey.gym_log_book.client.exercise.factory.IExerciseTypeEntityFactory
import net.michael_bailey.gym_log_book.client.exercise.factory.UuidV7ExerciseEntryFactory
import net.michael_bailey.gym_log_book.client.exercise.factory.UuidV7ExerciseTypeFactory
import net.michael_bailey.gym_log_book.client.exercise.repository.*
import net.michael_bailey.gym_log_book.client.exercise.service.*
import net.michael_bailey.gym_log_book.client.home.tabs.entry.ExerciseEntryTabViewModel
import net.michael_bailey.gym_log_book.client.home.tabs.entry.IExerciseEntryTabViewModel
import net.michael_bailey.gym_log_book.client.home.tabs.type.IExerciseTypeTabViewModel
import net.michael_bailey.gym_log_book.client.platform.AppDatabase
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.binds
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

	singleOf(::UuidV7ExerciseEntryFactory) bind IExerciseEntryEntityFactory::class
	singleOf(::UuidV7ExerciseTypeFactory) bind IExerciseTypeEntityFactory::class

	single {
		val nm = get<NetworkManager>()
		ExerciseEntryRepository(
			nm.getService(),
			get(),
			get(),
		)
	} binds arrayOf(
		IExerciseEntryNetworkRepository::class,
		IExerciseEntryRepository::class
	)

	single {
		val nm = get<NetworkManager>()
		ExerciseEntryDatabaseRepository(
			get(),
			get(),
			get(),
			get(),
		)
	} binds arrayOf(
		IExerciseEntryDatabaseRepository::class,
	)

	single {
		val nm = get<NetworkManager>()
		ExerciseTypeRepository(
			nm.getService(),
			get(),
			get(),
			get(),
		)
	} binds arrayOf(
		INetworkExerciseTypeRepository::class,
		ILocalExerciseTypeRepository::class,
	)

	singleOf(::ExerciseEntryService) binds arrayOf(
		INetworkExerciseEntryService::class,
		ILocalExerciseEntryService::class,
		IExerciseEntryService::class
	)
	singleOf(::ExerciseTypeService) binds arrayOf(
		ILocalExerciseTypeService::class,
		INetworkExerciseTypeService::class,
	)

	viewModelOf(::ExerciseEntryTabViewModel) bind IExerciseEntryTabViewModel::class
	viewModelOf(::ExerciseTypeTabViewModel) bind IExerciseTypeTabViewModel::class
}
