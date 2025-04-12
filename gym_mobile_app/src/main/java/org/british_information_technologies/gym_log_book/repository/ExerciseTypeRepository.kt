package org.british_information_technologies.gym_log_book.repository


import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.british_information_technologies.gym_log_book.data_type.EquipmentClass
import org.british_information_technologies.gym_log_book.database.dao.ExerciseEntryDao
import org.british_information_technologies.gym_log_book.database.dao.ExerciseTypeDao
import org.british_information_technologies.gym_log_book.database.entity.EntExerciseType
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ExerciseTypeRepository @Inject constructor(
	private val exerciseTypeDao: ExerciseTypeDao,
	private val exerciseEntryDao: ExerciseEntryDao
) {

	val exerciseTypes = exerciseTypeDao.genQueryAll()
	val isEmpty = exerciseTypeDao.exerciseTypeCount().map { it == 0 }

	/**
	 * creates a new exercise type.
	 */
	suspend fun create(
		name: String,
		usesUserWeight: Boolean,
		equipmentClass: EquipmentClass = EquipmentClass.None
	) {
		exerciseTypeDao.insertExercise(
			EntExerciseType(
				name = name,
				usesUserWeight = usesUserWeight,
				equipmentClass = equipmentClass
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
				usesUserWeight = usesUserWeight,
				equipmentClass = EquipmentClass.None
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

	fun genType(id: UUID): Flow<EntExerciseType> = exerciseTypeDao.genQuery(id)
	suspend fun updateType(ent: EntExerciseType) {
		exerciseTypeDao.updateExercise(ent)
	}

}