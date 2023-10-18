package org.british_information_technologies.gym_log_book.service

import android.app.Notification
import android.app.Service
import android.content.Intent
import android.os.IBinder
import dagger.hilt.android.AndroidEntryPoint
import io.github.michael_bailey.android_chat_kit.extension.any.log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import org.british_information_technologies.gym_log_book.lib.AppNotificationManager
import org.british_information_technologies.gym_log_book.repository.ExerciseEntryRepository
import org.british_information_technologies.gym_log_book.repository.ExerciseTypeRepository
import org.british_information_technologies.gym_log_book.repository.WeightEntryRepository
import org.british_information_technologies.gym_log_book.table.ExerciseTable
import org.british_information_technologies.gym_log_book.table.ExerciseTypeTable
import org.british_information_technologies.gym_log_book.table.WeightTable
import javax.inject.Inject

@AndroidEntryPoint
class DataImporterService : Service() {

	@Inject
	lateinit var appNotificationManager: AppNotificationManager

	@Inject
	lateinit var exerciseTypeTable: ExerciseTypeTable
	@Inject
	lateinit var exerciseTypeRepository: ExerciseTypeRepository

	@Inject
	lateinit var exerciseTable: ExerciseTable
	@Inject
	lateinit var exerciseEntryRepository: ExerciseEntryRepository

	@Inject
	lateinit var weightTable: WeightTable
	@Inject
	lateinit var weightEntryRepository: WeightEntryRepository

	private val job = SupervisorJob()
	private val serviceScope = CoroutineScope(Dispatchers.Main + job)

	/**
	 * The process for importing, follows a specific order
	 * This is because:
	 * - Exercises depend on both weight and exercise types.
	 * - Exercise types, may need to be imported from exercises.
	 * - weights are tied to the date of the exercise,
	 */
	override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

		log("Starting service")

		val notification: Notification =
			appNotificationManager.createServiceForegroundNotification()

		// Notification ID cannot be 0.
		startForeground(100, notification)

		serviceScope.launch {
			val exerciseTypeData = exerciseTypeTable.liveData.value!!
			val exerciseData = exerciseTable.liveData.value!!
			val weightData = weightTable.liveData.value!!

			// import exercise types
			val typeJob = serviceScope.launch {
				var jobArray = arrayListOf<Job>()

				for (i in exerciseTypeData) {
					jobArray.add(serviceScope.launch {
						exerciseTypeRepository.import(
							name = i.name,
							usesUserWeight = i.isUsingUserWeight
						)
					})
				}

				jobArray.joinAll()
			}


			// import weights
			val weightJob = serviceScope.launch {
				var jobArray = arrayListOf<Job>()

				for (i in weightData) {
					jobArray.add(serviceScope.launch {
						weightEntryRepository.import(
							createdDate = i.date,
							weight = i.weight,
						)
					})
				}
			}

			val entryJob = serviceScope.launch {

				typeJob.join()

				// this needs to be ~sync to allow imports to sync with queries
				for (i in exerciseData) {
					exerciseEntryRepository.import(
						createdDate = i.date,
						exerciseName = i.exercise,
						weight = i.weight,
						setNumber = i.setNumber,
						reps = i.reps
					)
				}
			}

			weightJob.join()
			entryJob.join()
			stopSelf()
		}

		return super.onStartCommand(intent, flags, startId)
	}

	override fun onBind(intent: Intent?): IBinder? {
		return null
	}
}