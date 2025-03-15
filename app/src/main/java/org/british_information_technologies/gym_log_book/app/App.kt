package org.british_information_technologies.gym_log_book.app

import android.app.Application
import com.google.android.material.color.DynamicColors
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CompletableJob
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancelChildren
import org.british_information_technologies.gym_log_book.database.AppDatabase
import javax.inject.Inject

@HiltAndroidApp
class App : Application() {

	@Inject
	lateinit var db: AppDatabase

	private val applicationSupervisor: CompletableJob = SupervisorJob()

	val applicationScope: CoroutineScope = CoroutineScope(applicationSupervisor + Dispatchers.Default)

	override fun onCreate() {
		super.onCreate()
		DynamicColors.applyToActivitiesIfAvailable(this)
	}

	override fun onTrimMemory(level: Int) {
		super.onTrimMemory(level)
		when (level) {
			TRIM_MEMORY_RUNNING_CRITICAL -> applicationSupervisor.cancelChildren()
			else -> {}
		}
	}

	override fun onLowMemory() {
		super.onLowMemory()

		applicationSupervisor.cancelChildren()
	}
}