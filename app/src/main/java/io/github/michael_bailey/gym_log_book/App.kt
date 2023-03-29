package io.github.michael_bailey.gym_log_book

import android.app.Application
import com.google.android.material.color.DynamicColors
import io.github.michael_bailey.gym_log_book.data_manager.ExerciseDataManager
import io.github.michael_bailey.gym_log_book.data_manager.WeightDataManager
import io.github.michael_bailey.gym_log_book.table.ExerciseTable
import io.github.michael_bailey.gym_log_book.table.WeightTable

class App : Application() {
	companion object {
		val TAG = "App"
	}

	internal lateinit var exerciseDataManager: ExerciseDataManager
	internal lateinit var weightDataManager: WeightDataManager

	internal lateinit var exerciseTable: ExerciseTable
	internal lateinit var weightTable: WeightTable


	override fun onCreate() {
		super.onCreate()
		DynamicColors.applyToActivitiesIfAvailable(this)

		exerciseDataManager = ExerciseDataManager(applicationContext)
		weightDataManager = WeightDataManager(applicationContext)

		exerciseTable = ExerciseTable(applicationContext)
		weightTable = WeightTable(applicationContext)


	}
}