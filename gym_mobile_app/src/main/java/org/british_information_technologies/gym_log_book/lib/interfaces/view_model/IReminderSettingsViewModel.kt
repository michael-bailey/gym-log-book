package org.british_information_technologies.gym_log_book.lib.interfaces.view_model

import androidx.lifecycle.LiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job

interface IReminderSettingsViewModel {

	val scope: CoroutineScope

	val calendarMap: Map<Long, String>?
	val selectedCalenderEntry: LiveData<Long?>

	val reminderCount: LiveData<Int>

	fun setCalendar(cal: Long): Job
}