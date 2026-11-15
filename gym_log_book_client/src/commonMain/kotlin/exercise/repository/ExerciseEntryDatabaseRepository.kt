@file:OptIn(ExperimentalUuidApi::class)

package net.michael_bailey.gym_log_book.client.exercise.repository

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.Flow
import net.michael_bailey.gym_log_book.client.exercise.entity.ExerciseEntryEntity
import net.michael_bailey.gym_log_book.client.exercise.factory.IExerciseEntryEntityFactory
import kotlin.time.Clock
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class ExerciseEntryDatabaseRepository(
	private val exerciseEntryEntityFactory: IExerciseEntryEntityFactory,
	private val dao: ExerciseEntryDao,
	private val clock: Clock,
	private val coroutineScope: CoroutineScope,
) : IExerciseEntryDatabaseRepository {

	override val allExerciseEntryIds: Flow<Collection<Uuid>>
		get() = dao.allExerciseEntryIdsFlow()

	fun getEntryFlow(
		id: Uuid,
	): Flow<ExerciseEntryEntity> = dao.exerciseEntryFlow(id)

	override suspend fun createNewExerciseEntry(
		exerciseTypeId: Uuid,
		entrySetNumber: Int,
		entryWeight: Double,
		entryReps: Int,
	) {
		val entry = exerciseEntryEntityFactory.create(
			exerciseTypeId = exerciseTypeId,
			setNumber = entrySetNumber,
			weight = entryWeight,
			reps = entryReps,
		)

		dao.createExerciseEntry(entry)
	}

	fun getEntry(id: Uuid): Deferred<ExerciseEntryEntity> = coroutineScope.async {
		dao.exerciseEntry(id)
	}

	fun deleteEntity(id: Uuid): Deferred<Result<Unit>> = coroutineScope.async {
		dao.exerciseEntry(id)
			.let { runCatching { dao.deleteExerciseEntry(it) } }
	}

	override fun deleteEntites(ids: List<Uuid>): Deferred<List<Throwable>> = coroutineScope.async {
		ids
			.map(::deleteEntity)
			.awaitAll()
			.mapNotNull { it.exceptionOrNull() }
	}
}