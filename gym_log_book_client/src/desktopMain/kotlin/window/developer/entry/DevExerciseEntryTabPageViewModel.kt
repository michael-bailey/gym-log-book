package net.michael_bailey.gym_log_book.client.window.developer.entry

import androidx.lifecycle.ViewModel
import net.michael_bailey.gym_log_book.client.exercise.service.ExerciseEntryService

class DevExerciseEntryTabPageViewModel(
	private val exerciseEntryService: ExerciseEntryService,
) : ViewModel()