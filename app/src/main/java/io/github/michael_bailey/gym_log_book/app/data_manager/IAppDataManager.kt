package io.github.michael_bailey.gym_log_book.app.data_manager

import io.github.michael_bailey.gym_log_book.data_manager.ExerciseDataManager
import io.github.michael_bailey.gym_log_book.data_manager.ExerciseTypeDataManager
import io.github.michael_bailey.gym_log_book.data_manager.WeightDataManager
import io.github.michael_bailey.gym_log_book.table.ExerciseTable
import io.github.michael_bailey.gym_log_book.table.WeightTable

interface IAppDataManager {
	val exerciseTypeDataManager: ExerciseTypeDataManager
	val exerciseDataManager: ExerciseDataManager
	val weightDataManager: WeightDataManager

	val exerciseTable: ExerciseTable
	val weightTable: WeightTable
}