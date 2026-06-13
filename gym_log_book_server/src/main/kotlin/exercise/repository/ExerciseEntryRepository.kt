@file:OptIn(ExperimentalUuidApi::class)

package net.michael_bailey.gym_log_book.server.exercise.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import net.michael_bailey.gym_log_book.server.database.schema.v1.ExerciseEntryTable
import net.michael_bailey.gym_log_book.server.exercise.domain.ExerciseEntryModel
import net.michael_bailey.gym_log_book.server.exercise.domain.NewExerciseEntryModel
import net.michael_bailey.gym_log_book.server.exercise.mapper.toExerciseEntryDomain
import org.jetbrains.exposed.v1.jdbc.Database
import org.jetbrains.exposed.v1.jdbc.insertReturning
import org.jetbrains.exposed.v1.jdbc.selectAll
import org.jetbrains.exposed.v1.jdbc.transactions.transaction
import org.koin.core.annotation.Single
import kotlin.uuid.ExperimentalUuidApi

@Single(binds = [IExerciseEntryRepository::class])
class ExerciseEntryRepository(
	private val database: Database,
) : IExerciseEntryRepository {

	private val _allExerciseEntries = MutableStateFlow<Collection<ExerciseEntryModel>>(emptyList())

	override val exerciseEntries: Flow<Collection<ExerciseEntryModel>>
		get() = _allExerciseEntries

	init {
		val initialItems = transaction(database) {
			ExerciseEntryTable.selectAll()
				.map { it.toExerciseEntryDomain() }
		}
		_allExerciseEntries.value = initialItems
	}

	override suspend fun createEntry(
		newExerciseEntry: NewExerciseEntryModel
	): ExerciseEntryModel = transaction {
		ExerciseEntryTable.insertReturning {
			it[this.exerciseTypeId] = newExerciseEntry.exerciseTypeId
			it[this.createTime] = newExerciseEntry.creationInstant
			it[this.createTime] = newExerciseEntry.creationInstant
			it[this.setNumber] = newExerciseEntry.setNumber
			it[this.weight] = newExerciseEntry.weight
			it[this.reps] = newExerciseEntry.reps
		}.first().toExerciseEntryDomain().also {
			_allExerciseEntries.value += listOf(it)
		}
	}
}