package net.michael_bailey.gym_log_book.server.exercise.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import net.michael_bailey.gym_log_book.server.exercise.domain.ExerciseEntryModel
import net.michael_bailey.gym_log_book.server.exercise.domain.NewExerciseEntryModel
import net.michael_bailey.gym_log_book.server.exercise.factory.InMemoryExerciseEntryFactory
import org.koin.core.annotation.Single

@Single(binds = [IExerciseEntryRepository::class])
class InMemoryExerciseEntryRepository(
	private val exerciseEntryFactory: InMemoryExerciseEntryFactory,
) : IExerciseEntryRepository {

	private val _exerciseEntries = MutableStateFlow<List<ExerciseEntryModel>>(emptyList())

	override val exerciseEntries: Flow<Collection<ExerciseEntryModel>> = _exerciseEntries

	override suspend fun createEntry(newExerciseEntry: NewExerciseEntryModel): ExerciseEntryModel {
		val entry = exerciseEntryFactory.createEntry(newExerciseEntry)
		_exerciseEntries.emit(_exerciseEntries.value + entry)
		return entry
	}
}
