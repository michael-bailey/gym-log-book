package io.github.michael_bailey.gym_log_book.view_model

data class AddExerciseSetViewModelState(
	var exercise: String = "",
	var setNumber: Long = 1,
	var weight: Long = 0,
	var reps: Long = 0,
)