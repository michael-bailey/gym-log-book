package net.michael_bailey.gym_log_book.client.exercise.service

import net.michael_bailey.gym_log_book.shared.exercise.controller.ExerciseEntryController

class ExerciseEntryService(
	private val exerciseEntryController: ExerciseEntryController
) {

	val allEntries = exerciseEntryController.getExerciseEntries()

}