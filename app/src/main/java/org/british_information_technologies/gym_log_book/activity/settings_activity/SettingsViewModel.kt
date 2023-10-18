package org.british_information_technologies.gym_log_book.activity.settings_activity

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.michael_bailey.android_chat_kit.extension.any.log
import org.british_information_technologies.gym_log_book.repository.ReminderRepository
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
	reminderRepository: ReminderRepository
) : ViewModel() {

	init {
		log("init")
	}

}