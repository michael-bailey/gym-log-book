package io.github.michael_bailey.gym_log_book.lib

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context.NOTIFICATION_SERVICE
import io.github.michael_bailey.gym_log_book.App
import io.github.michael_bailey.gym_log_book.R

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

	fun postTimerNotification() {
		notificationManager.apply {
			val notification = Notification
				.Builder(application, timerNotificationChannel.id).apply {
					setSmallIcon(R.drawable.ic_launcher_foreground)
					setContentTitle("Timer Up!")
					setContentText("Time to start your next set")
					notificationManager.notify(1, build())
				}

		}
	}
}