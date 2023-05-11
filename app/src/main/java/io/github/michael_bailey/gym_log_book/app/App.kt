package io.github.michael_bailey.gym_log_book.app

import android.app.Application
import androidx.room.Room
import com.google.android.material.color.DynamicColors
import io.github.michael_bailey.gym_log_book.app.data_manager.AppDataManager
import io.github.michael_bailey.gym_log_book.app.data_manager.IAppDataManager
import io.github.michael_bailey.gym_log_book.app.notification.AppNotification
import io.github.michael_bailey.gym_log_book.app.notification.IAppNotification
import io.github.michael_bailey.gym_log_book.app.preferences.AppPreferences
import io.github.michael_bailey.gym_log_book.app.preferences.IAppPreference
import io.github.michael_bailey.gym_log_book.database.AppDatabase
import io.github.michael_bailey.gym_log_book.database.importer.AbstractImporter
import io.github.michael_bailey.gym_log_book.database.importer.ExerciseTypeImporter
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class App : Application(),
	IAppPreference by AppPreferences(this),
	IAppDataManager by AppDataManager(this),
	IAppNotification by AppNotification(this),
	ExecutorService by Executors.newCachedThreadPool() {

	lateinit var db: AppDatabase

	private val importers: List<Class<out AbstractImporter<Any, Any>>> =
		listOf(
			ExerciseTypeImporter::class.java,
		) as List<Class<out AbstractImporter<Any, Any>>>

	init {
		instance = this
	}

	override fun onCreate() {
		super.onCreate()
		DynamicColors.applyToActivitiesIfAvailable(this)

		db = Room.inMemoryDatabaseBuilder(
			applicationContext,
			AppDatabase::class.java,
		).build().apply {
			importers.map {
				val constructor = it.getConstructor(App::class.java)
				submit(constructor.newInstance(this@App))
			}
		}
	}

	companion object {
		private lateinit var instance: App
		fun getInstance(): App {
			return instance
		}
	}
}