package org.british_information_technologies.gym_watch_app.app

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CompletableJob
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

@HiltAndroidApp
class App : Application() {
	private val applicationSupervisor: CompletableJob = SupervisorJob()
	val applicationScope: CoroutineScope = CoroutineScope(applicationSupervisor + Dispatchers.Default)
}