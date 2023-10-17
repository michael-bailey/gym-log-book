package io.github.michael_bailey.gym_log_book.theme

import android.app.NotificationChannel
import android.app.NotificationManager

// constant notifcation channel ids
val TimerNotificationChannel = NotificationChannel(
	"TimerChannel",
	"Timer Notifications",
	NotificationManager.IMPORTANCE_HIGH
).apply {
	enableVibration(true)
}

// constant notification ids
val TimerNotification = 1