package org.british_information_technologies.gym_log_book.app

import android.app.Application
import com.google.android.material.color.DynamicColors
import dagger.hilt.android.HiltAndroidApp
import org.british_information_technologies.gym_log_book.database.AppDatabase
import javax.inject.Inject

@HiltAndroidApp
class App : Application() {

	@Inject
	lateinit var db: AppDatabase

	override fun onCreate() {
		super.onCreate()
		DynamicColors.applyToActivitiesIfAvailable(this)
	}
}