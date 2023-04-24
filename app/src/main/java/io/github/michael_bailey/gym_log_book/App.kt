package io.github.michael_bailey.gym_log_book

import android.app.Application
import androidx.room.Room
import com.google.android.material.color.DynamicColors
import io.github.michael_bailey.gym_log_book.data_manager.ExerciseDataManager
import io.github.michael_bailey.gym_log_book.data_manager.ExerciseTypeDataManager
import io.github.michael_bailey.gym_log_book.data_manager.WeightDataManager
import io.github.michael_bailey.gym_log_book.database.AppDatabase
import io.github.michael_bailey.gym_log_book.lib.AppNotificationManager
import io.github.michael_bailey.gym_log_book.lib.DebugPreferencesManager
import io.github.michael_bailey.gym_log_book.lib.PreferencesManager
import io.github.michael_bailey.gym_log_book.table.ExerciseTable
import io.github.michael_bailey.gym_log_book.table.WeightTable

class App : Application() {
	companion object {
		private lateinit var instance: App
		fun getInstance(): App {
			return instance
		}
	}

	internal lateinit var appPreferencesManager: PreferencesManager
	internal lateinit var appDebugPreferencesManager: DebugPreferencesManager
	internal lateinit var appNotificationManager: AppNotificationManager

	internal lateinit var exerciseTypeDataManager: ExerciseTypeDataManager
	internal lateinit var exerciseDataManager: ExerciseDataManager
	internal lateinit var weightDataManager: WeightDataManager

	lateinit var exerciseTable: ExerciseTable
	lateinit var weightTable: WeightTable

	lateinit var db: AppDatabase


	override fun onCreate() {
		super.onCreate()

		instance = this

		DynamicColors.applyToActivitiesIfAvailable(this)

		exerciseTypeDataManager = ExerciseTypeDataManager(applicationContext)
		exerciseDataManager = ExerciseDataManager(applicationContext)
		weightDataManager = WeightDataManager(applicationContext)

		exerciseTable = ExerciseTable(applicationContext)
		weightTable = WeightTable(applicationContext)

		appDebugPreferencesManager = DebugPreferencesManager(this)
		appPreferencesManager = PreferencesManager(this)

		appNotificationManager = AppNotificationManager(this)

		db = Room.databaseBuilder(
			applicationContext,
			AppDatabase::class.java,
			"gym_log_book_db"
		).build()
	}
}