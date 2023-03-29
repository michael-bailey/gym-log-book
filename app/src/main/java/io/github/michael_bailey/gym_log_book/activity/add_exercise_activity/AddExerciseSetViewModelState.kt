package io.github.michael_bailey.gym_log_book.activity.add_exercise_activity

data class AddExerciseSetViewModelState(
	var exercise: String = "",
	var setNumber: Int = 1,
	var weight: Double = 0.0,
	var reps: Int = 0,
)