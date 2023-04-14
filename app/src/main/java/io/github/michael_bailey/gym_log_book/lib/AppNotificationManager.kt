package io.github.michael_bailey.gym_log_book.lib

import android.app.*
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import android.net.Uri
import io.github.michael_bailey.gym_log_book.App
import io.github.michael_bailey.gym_log_book.R
import io.github.michael_bailey.gym_log_book.activity.exercise_set_guide_activity.ExerciseSetGuideActivity
import io.github.michael_bailey.gym_log_book.activity.main_activity_v2.MainActivityV2
import io.github.michael_bailey.gym_log_book.theme.scheme

class AppNotificationManager(
	val application: App,

	private val timerNotificationChannel: NotificationChannel =
		NotificationChannel(
			"TimerChannel",
			"Timer Notifications",
			NotificationManager.IMPORTANCE_HIGH
		).apply {
			enableVibration(true)
		},
) {
	val notificationManager: NotificationManager =
		(application.getSystemService(NOTIFICATION_SERVICE) as NotificationManager)
			.apply {
				createNotificationChannel(timerNotificationChannel)
			}

	fun cancelTimerNotification() {
		notificationManager.cancel(1)
	}

	fun postTimerNotification(
		ctx: Context,
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

		val mainActivityintent = Intent(ctx, MainActivityV2::class.java)
		val setGuideIntent =
			Intent(ctx, ExerciseSetGuideActivity::class.java).apply {
				data = guideData
			}
		val notificationIntent: PendingIntent? = TaskStackBuilder.create(ctx).run {
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