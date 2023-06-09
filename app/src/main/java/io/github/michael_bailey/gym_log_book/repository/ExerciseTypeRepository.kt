package io.github.michael_bailey.gym_log_book.repository

import io.github.michael_bailey.gym_log_book.database.dao.ExerciseEntryDao
import io.github.michael_bailey.gym_log_book.database.dao.ExerciseTypeDao
import io.github.michael_bailey.gym_log_book.database.entity.EntExerciseType
import kotlinx.coroutines.flow.map
import java.util.UUID
import javax.inject.Inject

class ExerciseTypeRepository @Inject constructor(
	private val exerciseTypeDao: ExerciseTypeDao,
	private val exerciseEntryDao: ExerciseEntryDao
) {

	val exerciseTypes = exerciseTypeDao.queryAllExerciseTypeFlow()
	val exerciseTypeMap =
		exerciseTypes.map { types -> types.associateBy { it.id } }
	val isEmpty = exerciseTypeDao.exerciseTypeCount().map { it == 0 }

	/**
	 * creates a new exercise type.
	 */
	suspend fun create(
		name: String,
		usesUserWeight: Boolean
	) {
		exerciseTypeDao.insertExercise(
			EntExerciseType(
				name = name,
				usesUserWeight = usesUserWeight
			)
		)
	}

	/**
	 * used for importing, this includes the date created.
	 */
	suspend fun import(
		name: String,
		usesUserWeight: Boolean
	) {
		exerciseTypeDao.insertExercise(
			EntExerciseType(
				name = name,
				usesUserWeight = usesUserWeight
			)
		)
	}

	/**
	 * deletes a exercise by an idea
	 */
	suspend fun delete(id: UUID) {
		exerciseTypeDao.queryExercise(id)
			?.let { exerciseTypeDao.deleteExercise(it) }
	}

	suspend fun getExerciseType(exerciseTypeID: UUID?): EntExerciseType? =
		exerciseTypeID?.let { exerciseTypeDao.queryExercise(it) }

	suspend fun removeAndReplaceType(removedType: UUID, replacementType: UUID) {
		val exercises = exerciseEntryDao.getExercisesByType(removedType)

		val updatedExercises =
			exercises.map { it.copy(exerciseTypeId = replacementType) }

		exerciseEntryDao.updateExercises(updatedExercises)

		exerciseTypeDao.deleteExercise(exerciseTypeDao.queryExercise(removedType)!!)
	}
}