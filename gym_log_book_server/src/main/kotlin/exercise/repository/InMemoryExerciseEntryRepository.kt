@file:OptIn(ExperimentalUuidApi::class)

package net.michael_bailey.gym_log_book.server.exercise.repository

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import net.michael_bailey.gym_log_book.server.exercise.factory.InMemoryExerciseEntryFactory
import net.michael_bailey.gym_log_book.shared.exercise.model.ExerciseEntry
import org.koin.core.annotation.Single
import kotlin.time.Clock
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@Single
class InMemoryExerciseEntryRepository(
	private val exerciseEntryFactory: InMemoryExerciseEntryFactory,
	private val scope: CoroutineScope,
) : IExerciseEntryRepository {

	private val _mapState: MutableStateFlow<Map<Uuid, ExerciseEntry>> = MutableStateFlow(
		buildMap {
			(0 until 5).forEach { _ ->
				val id = Uuid.random()
				this[id] = ExerciseEntry(
					id = id,
					date = Clock.System.now().toLocalDateTime(TimeZone.UTC),
					exerciseTypeId = Uuid.random(),
					exerciseTypeName = "Random Type",
					setNumber = 3,
					weight = 12.5,
					reps = 12
				)
			}
		}
	)

	override val exerciseEntries: Flow<Collection<ExerciseEntry>> = _mapState.map { it.values }

	override fun createNewEntry(
		exerciseTypeId: Uuid,
		entrySetNumber: Int,
		entryWeight: Double,
		entryReps: Int
	): Deferred<ExerciseEntry> = scope.async {

		val entry = exerciseEntryFactory.createEntry(
			exerciseTypeId = exerciseTypeId,
			entrySetNumber = entrySetNumber,
			entryWeight = entryWeight,
			entryReps = entryReps,
		)

		_mapState.emit(_mapState.value + mapOf(entry.id to entry))

		entry
	}

}