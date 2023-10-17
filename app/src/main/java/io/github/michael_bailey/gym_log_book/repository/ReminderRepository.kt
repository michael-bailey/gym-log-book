package io.github.michael_bailey.gym_log_book.repository

import android.content.ContentResolver
import android.content.Context
import android.database.Cursor
import android.provider.CalendarContract
import dagger.hilt.android.qualifiers.ApplicationContext
import io.github.michael_bailey.android_chat_kit.extension.any.log
import javax.inject.Inject

class ReminderRepository @Inject constructor(
	private val resolver: ContentResolver,
	@ApplicationContext private val context: Context
) {

	companion object {
		// Projection array. Creating indices for this array instead of doing
		// dynamic lookups improves performance.
		private val CALENDAR_COLS: Array<String> = arrayOf(
			CalendarContract.Calendars._ID,                     // 0
			CalendarContract.Calendars.ACCOUNT_NAME,            // 1
			CalendarContract.Calendars.CALENDAR_DISPLAY_NAME,   // 2
			CalendarContract.Calendars.OWNER_ACCOUNT            // 3
		)

		private val CALENDAR_URI = CalendarContract.Calendars.CONTENT_URI

		// how to get the data
		private const val PROJECTION_ID_INDEX: Int = 0
		private const val PROJECTION_ACCOUNT_NAME_INDEX: Int = 1
		private const val PROJECTION_DISPLAY_NAME_INDEX: Int = 2
		private const val PROJECTION_OWNER_ACCOUNT_INDEX: Int = 3

		data class UserCalender(
			val calID: Long,
			val displayName: String,
			val accountName: String,
			val ownerName: String,
		)
	}

	fun queryCalendars(): List<UserCalender>? {

		var cur: Cursor? = null
		try {
			cur = resolver.query(CALENDAR_URI, CALENDAR_COLS, null, null, null)
			log("loaded calendars :)")
		} catch (e: SecurityException) {
			log("cannot load calendars due to permissions")
		}

		return cur?.use {
			it.moveToFirst()

			if (it.count == 0) {
				log("no calendars")
				return null
			}

			log("calendar count ${it.count}")

			val calList = mutableListOf<UserCalender>()
			do {
				val calID: Long = it.getLong(PROJECTION_ID_INDEX)
				val displayName: String = it.getString(PROJECTION_DISPLAY_NAME_INDEX)
				val accountName: String = it.getString(PROJECTION_ACCOUNT_NAME_INDEX)
				val ownerName: String = it.getString(PROJECTION_OWNER_ACCOUNT_INDEX)
				log("got details, calID:$calID, display name:$displayName, account name:$accountName, owner name:$ownerName")
				calList.add(
					UserCalender(
						calID,
						displayName,
						accountName,
						ownerName,
					)
				)
			} while (it.moveToNext())
			calList
		}
	}
}