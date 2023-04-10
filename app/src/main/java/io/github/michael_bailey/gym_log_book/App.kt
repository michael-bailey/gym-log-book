package io.github.michael_bailey.gym_log_book

import android.app.Application
import com.google.android.material.color.DynamicColors
import io.github.michael_bailey.gym_log_book.data_manager.ExerciseDataManager
import io.github.michael_bailey.gym_log_book.data_manager.ExerciseTypeDataManager
import io.github.michael_bailey.gym_log_book.data_manager.WeightDataManager
import io.github.michael_bailey.gym_log_book.table.ExerciseTable
import io.github.michael_bailey.gym_log_book.table.WeightTable

class App : Application() {
	companion object {
		val TAG = "App"
	}

	internal lateinit var exerciseTypeDataManager: ExerciseTypeDataManager
	internal lateinit var exerciseDataManager: ExerciseDataManager
	internal lateinit var weightDataManager: WeightDataManager

	lateinit var exerciseTable: ExerciseTable
	lateinit var weightTable: WeightTable


	override fun onCreate() {
		super.onCreate()
		DynamicColors.applyToActivitiesIfAvailable(this)

		exerciseTypeDataManager = ExerciseTypeDataManager(applicationContext)
		exerciseDataManager = ExerciseDataManager(applicationContext)
		weightDataManager = WeightDataManager(applicationContext)

		exerciseTable = ExerciseTable(applicationContext)
		weightTable = WeightTable(applicationContext)
	}
}