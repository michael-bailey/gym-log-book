package io.github.michael_bailey.gym_log_book.activity.settings_activity.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import io.github.michael_bailey.gym_log_book.lib.interfaces.view_model.IReminderSettingsViewModel
import io.github.michael_bailey.gym_log_book.repository.ReminderRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class ReminderSettingsViewModel(
	private val reminderRepository: ReminderRepository,
) : IReminderSettingsViewModel {
	override val scope: CoroutineScope = GlobalScope

	private val _selectedCalenderEntry = MutableStateFlow<Long?>(null)
	private val _calendars = reminderRepository.queryCalendars()
	override val calendarMap: Map<Long, String>?
		get() = _calendars?.map { it.calID to it.displayName }?.toMap()
	override val selectedCalenderEntry: LiveData<Long?> =
		_selectedCalenderEntry.asLiveData()

	override val reminderCount: LiveData<Int>
		get() = TODO("Not yet implemented")

	override fun setCalendar(cal: Long) = scope.launch {
		_selectedCalenderEntry.emit(cal)
	}
}