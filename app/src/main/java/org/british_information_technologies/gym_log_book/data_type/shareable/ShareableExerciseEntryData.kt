package org.british_information_technologies.gym_log_book.data_type.shareable

import kotlinx.serialization.Serializable
import org.british_information_technologies.gym_log_book.lib.serialisation.LocalDateTimeSerialiser
import java.time.LocalDateTime

@Serializable
data class ShareableExerciseEntryData(
	@Serializable(with = LocalDateTimeSerialiser::class)
	var created: LocalDateTime,

	var exerciseType: String,
	var setNumber: Int,
	var weight: Double,
	var reps: Int,
)
