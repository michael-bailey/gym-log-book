@file:OptIn(ExperimentalUuidApi::class)

package net.michael_bailey.gym_log_book.client.exercise.service

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import net.michael_bailey.gym_log_book.client.exercise.entity.ExerciseEntryEntity
import net.michael_bailey.gym_log_book.client.exercise.repository.ExerciseEntryDatabaseRepository
import net.michael_bailey.gym_log_book.client.exercise.repository.ExerciseEntryRepository
import net.michael_bailey.gym_log_book.client.exercise.repository.ExerciseTypeRepository
import net.michael_bailey.gym_log_book.client.exercise.repository.ILocalExerciseTypeRepository
import net.michael_bailey.gym_log_book.shared.exercise.model.ExerciseEntryView
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

/**
 * # Overview
 * A service object that provides exercise entry information.
 *
 * - Projects entry and type data to provide views for viewModels
 * - Provides CRUD operations for entry data to the local database.
 *
 * ## TODO
 * - Remove networking repositories and interface only with the database
 */
class ExerciseEntryService(
	private val typeRepository: ExerciseTypeRepository,
	private val typeDatabaseRepository: ILocalExerciseTypeRepository,
	private val entryRepository: ExerciseEntryRepository,
	private val entryDatabaseRepository: ExerciseEntryDatabaseRepository,
) : INetworkExerciseEntryService, ILocalExerciseEntryService {

	override val allEntries = entryRepository.allExerciseEntries

	override val allLocalEntryIds: Flow<Collection<Uuid>> =
		entryDatabaseRepository.allExerciseEntryIds

	override suspend fun getLocalEntry(id: Uuid): ExerciseEntryEntity =
		entryDatabaseRepository.getEntry(id).await()

	override suspend fun createNewExerciseEntry(
		exerciseTypeId: Uuid,
		entrySetNumber: Int,
		entryWeight: Double,
		entryReps: Int,
	) {
		entryRepository.createNewExerciseEntry(
			exerciseTypeId = exerciseTypeId,
			entrySetNumber = entrySetNumber,
			entryWeight = entryWeight,
			entryReps = entryReps,
		)

	}

	//mark: - Local methods

	override suspend fun createNewLocalEntry(
		exerciseTypeId: Uuid,
		entrySetNumber: Int,
		entryWeight: Double,
		entryReps: Int,
	) {
		entryDatabaseRepository.createNewExerciseEntry(
			exerciseTypeId = exerciseTypeId,
			entrySetNumber = entrySetNumber,
			entryWeight = entryWeight,
			entryReps = entryReps,
		)
	}

	override suspend fun updateLocalEntry() {
		TODO("Not yet implemented")
	}

	override suspend fun deleteLocalEntry() {
		TODO("Not yet implemented")
	}

	/**
	 * # Overview
	 * Get up-to-date individual entries, from the local database.
	 *
	 * Uses type and entry repository to project to an entry view.
	 *
	 * ## TODO
	 * - make this use the local database for types as well
	 */
	fun getLocalExerciseEntryFlow(id: Uuid): Flow<ExerciseEntryView> = combine(
		entryDatabaseRepository.getEntryFlow(id),
		typeRepository.allNetworkExerciseTypesMap
	) { entry, types ->
		ExerciseEntryView(
			entry.id,
			// todo: convert this to use the users timezone
			(entry.date).toLocalDateTime(TimeZone.UTC),
			entry.exerciseTypeId,
			types[entry.exerciseTypeId]?.name ?: "Unknown Type",
			entry.setNumber,
			entry.weight,
			entry.reps,
		)
	}

}
