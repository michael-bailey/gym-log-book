package org.british_information_technologies.gym_log_book.lib

import android.app.Application
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import android.net.Uri
import org.british_information_technologies.gym_log_book.R
import org.british_information_technologies.gym_log_book.activity.exercise_set_guide_activity.ExerciseSetGuideActivity
import org.british_information_technologies.gym_log_book.activity.exercise_set_guide_activity.ExerciseSetGuideActivityIntentUtils
import org.british_information_technologies.gym_log_book.activity.main_activity.MainActivity
import org.british_information_technologies.gym_log_book.theme.Scheme
import org.british_information_technologies.gym_log_book.theme.TimerNotification
import org.british_information_technologies.gym_log_book.theme.TimerNotificationChannel
import java.util.UUID
import javax.inject.Inject

class AppNotificationManager @Inject constructor(
	val application: Application,
) {

	private val timerNotificationChannel: NotificationChannel =
		TimerNotificationChannel


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
			.setSmallIcon(R.drawable.app_icon_foreground)
			.setOngoing(true)
			.setCategory(Notification.CATEGORY_SERVICE)
			.build()

	fun cancelTimerNotification() {
		notificationManager.cancel(1)
	}


	@Deprecated("Old code for posting notifications")
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
					setSmallIcon(R.drawable.app_icon_foreground)
					setContentTitle("Timer Up!")
					setContentIntent(notificationIntent)
					setContentText("Time to start your next set")
					notificationManager.notify(1, build())
				}
		}
	}

	fun createTimerNotificationPoster(
		exercise: UUID,
		set: Int,
		weight: Double
	): () -> Unit {

		val guideData = Uri.Builder().run {
			scheme(Scheme)
			path("/setGuideActivity/resume")
			appendQueryParameter("exercise", exercise.toString())
			appendQueryParameter("set", set.toString())
			appendQueryParameter("weight", weight.toString())
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
		val build = Notification
			.Builder(application, timerNotificationChannel.id)
			.apply {
				setSmallIcon(R.drawable.app_icon_foreground)
				setContentTitle("Timer Up!")
				setContentIntent(notificationIntent)
				setContentText("Time to start your next set")
			}::build

		return {
			notificationManager.notify(TimerNotification, build())
		}
	}
}
