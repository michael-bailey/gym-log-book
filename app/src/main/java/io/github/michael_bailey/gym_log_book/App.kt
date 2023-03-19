package io.github.michael_bailey.gym_log_book

import android.app.Application
import io.github.michael_bailey.gym_log_book.table.ExerciseTable
import io.github.michael_bailey.gym_log_book.table.WeightTable

class App : Application() {
	companion object {
		val TAG = "App"
	}

	internal lateinit var exerciseTable: ExerciseTable
	internal lateinit var weightTable: WeightTable


	override fun onCreate() {
		super.onCreate()

		exerciseTable = ExerciseTable(applicationContext)
		weightTable = WeightTable(applicationContext)


	}
}