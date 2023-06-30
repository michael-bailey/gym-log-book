package io.github.michael_bailey.gym_log_book.lib

import android.app.Application
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import android.net.Uri
import io.github.michael_bailey.gym_log_book.R
import io.github.michael_bailey.gym_log_book.activity.exercise_set_guide_activity.ExerciseSetGuideActivity
import io.github.michael_bailey.gym_log_book.activity.exercise_set_guide_activity.ExerciseSetGuideActivityIntentUtils
import io.github.michael_bailey.gym_log_book.activity.main_activity.MainActivity
import io.github.michael_bailey.gym_log_book.theme.scheme
import java.util.UUID
import javax.inject.Inject

class AppNotificationManager @Inject constructor(
	val application: Application,
) {

	private val timerNotificationChannel: NotificationChannel =
		NotificationChannel(
			"TimerChannel",
			"Timer Notifications",
			NotificationManager.IMPORTANCE_HIGH
		).apply {
			enableVibration(true)
		}

	val serviceChannel: NotificationChannel =
		NotificationChannel(
			"ServiceChannel",
			"Service Notifications",
			NotificationManager.IMPORTANCE_LOW
		).apply {
			enableVibration(false)
			enableLights(false)
		}

	val notificationManager: NotificationManager =
		(application.getSystemService(NOTIFICATION_SERVICE) as NotificationManager)

	init {
		notificationManager.createNotificationChannel(timerNotificationChannel)
		notificationManager.createNotificationChannel(serviceChannel)
	}

	fun createServiceForegroundNotification(): Notification =
		Notification.Builder(application, this.serviceChannel.id)
			.setContentTitle("Migration In Progress")
			.setContentText("There is a data migration in progress, please wait")
			.setProgress(100, 0, true)
			.setSmallIcon(R.drawable.ic_launcher_foreground)
			.setOngoing(true)
			.setCategory(Notification.CATEGORY_SERVICE)
			.build()

	fun cancelTimerNotification() {
		notificationManager.cancel(1)
	}


	fun postTimerNotification(
		exerciseType: UUID,
		set: Int,
		weight: Double
	) {

		val setGuideIntent =
			ExerciseSetGuideActivityIntentUtils.createExerciseSetGuideActivityIntent(
				application,
				exerciseType,
				set,
				weight
			)

		val mainActivityIntent = Intent(application, MainActivity::class.java)

		val notificationIntent: PendingIntent? =
			TaskStackBuilder.create(application).run {
				addNextIntentWithParentStack(mainActivityIntent)
				addNextIntentWithParentStack(setGuideIntent)
				getPendingIntent(
					0,
					PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
				)
			}

		notificationManager.apply {
			Notification
				.Builder(application, timerNotificationChannel.id)
				.apply {
					setSmallIcon(R.drawable.ic_launcher_foreground)
					setContentTitle("Timer Up!")
					setContentIntent(notificationIntent)
					setContentText("Time to start your next set")
					notificationManager.notify(1, build())
				}
		}

		@Deprecated(" Old code used for non database stuff")
		fun postTimerNotification(
			exercise: String,
			set: String,
			weight: String
		) {

			val guideData = Uri.Builder().run {
				scheme(scheme)
				path("/setGuideActivity/resume")
				appendQueryParameter("exercise", exercise)
				appendQueryParameter("set", set)
				appendQueryParameter("weight", weight)
				build()
			}

			val mainActivityintent = Intent(application, MainActivity::class.java)
			val setGuideIntent =
				Intent(application, ExerciseSetGuideActivity::class.java).apply {
					data = guideData
				}
			val notificationIntent: PendingIntent? =
				TaskStackBuilder.create(application).run {
					addNextIntentWithParentStack(mainActivityintent)
					addNextIntentWithParentStack(setGuideIntent)
					getPendingIntent(
						0,
						PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
					)
				}


			notificationManager.apply {
				Notification
					.Builder(application, timerNotificationChannel.id)
					.apply {
						setSmallIcon(R.drawable.ic_launcher_foreground)
						setContentTitle("Timer Up!")
						setContentIntent(notificationIntent)
						setContentText("Time to start your next set")
						notificationManager.notify(1, build())
					}
			}
		}
	}
}