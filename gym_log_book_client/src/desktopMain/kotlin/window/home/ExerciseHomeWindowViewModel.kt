package net.michael_bailey.gym_log_book.client.window.home

import androidx.lifecycle.ViewModel
import net.michael_bailey.gym_log_book.client.exercise.service.ExerciseEntryService

class ExerciseHomeWindowViewModel(
	val exerciseEntryService: ExerciseEntryService
) : ViewModel()