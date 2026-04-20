@file:OptIn(ExperimentalUuidApi::class)

package net.michael_bailey.gym_log_book.server.exercise.factory

import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import net.michael_bailey.gym_log_book.shared.exercise.model.ExerciseEntry
import org.koin.core.annotation.Factory
import kotlin.time.Clock
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@Factory
class InMemoryExerciseEntryFactory(
	private val clock: Clock,
) : IExerciseEntryFactory {
	override fun createEntry(
		exerciseTypeId: Uuid,
		entrySetNumber: Int,
		entryWeight: Double,
		entryReps: Int
	): ExerciseEntry {

		val id = Uuid.random()

		return ExerciseEntry(
			id = id,
			date = clock.now().toLocalDateTime(TimeZone.UTC),
			exerciseTypeId = exerciseTypeId,
			setNumber = entrySetNumber,
			weight = entryWeight,
			reps = entryReps,
		)
	}
}