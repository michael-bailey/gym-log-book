package io.github.michael_bailey.gym_log_book.app.data_manager

import io.github.michael_bailey.gym_log_book.app.App
import io.github.michael_bailey.gym_log_book.data_manager.ExerciseDataManager
import io.github.michael_bailey.gym_log_book.data_manager.ExerciseTypeDataManager
import io.github.michael_bailey.gym_log_book.data_manager.WeightDataManager
import io.github.michael_bailey.gym_log_book.table.ExerciseTable
import io.github.michael_bailey.gym_log_book.table.WeightTable

open class AppDataManager(
	private val companion: App.Companion
) : IAppDataManager {

	override val exerciseTypeDataManager by
	lazy { ExerciseTypeDataManager(companion.getInstance()) }
	override val exerciseDataManager by
	lazy { ExerciseDataManager(companion.getInstance()) }
	override val weightDataManager by
	lazy { WeightDataManager(companion.getInstance()) }

	override val exerciseTable by lazy { ExerciseTable(companion.getInstance()) }
	override val weightTable by lazy { WeightTable(companion.getInstance()) }

}