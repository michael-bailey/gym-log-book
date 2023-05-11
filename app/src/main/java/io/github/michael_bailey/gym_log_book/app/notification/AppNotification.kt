package io.github.michael_bailey.gym_log_book.app.notification

import io.github.michael_bailey.gym_log_book.app.App
import io.github.michael_bailey.gym_log_book.lib.AppNotificationManager

class AppNotification(val companion: App.Companion) : IAppNotification {
	override val appNotificationManager: AppNotificationManager by
	lazy { AppNotificationManager(App.getInstance()) }
}