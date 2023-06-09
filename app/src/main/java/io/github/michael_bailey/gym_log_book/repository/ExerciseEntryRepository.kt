package io.github.michael_bailey.gym_log_book.repository

import io.github.michael_bailey.gym_log_book.database.dao.ExerciseEntryDao
import io.github.michael_bailey.gym_log_book.database.dao.ExerciseTypeDao
import io.github.michael_bailey.gym_log_book.database.entity.EntExerciseEntry
import io.github.michael_bailey.gym_log_book.database.entity.EntExerciseType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.UUID
import javax.inject.Inject

class ExerciseEntryRepository @Inject constructor(
	private val exerciseEntryDao: ExerciseEntryDao,
	private val exerciseTypeDao: ExerciseTypeDao
) {

	val exercises = exerciseEntryDao.flowAllExercise()
	val exerciseCount = exerciseEntryDao.exerciseCount()
	val isEmpty = exerciseCount.map { it == 0 }

	val timeExerciseList: Flow<List<EntExerciseEntry>> = exercises.map { list ->
		list.sortedBy {
			LocalDateTime.of(it.createdDate, it.createdTime)
		}
	}

	val timeExerciseGroupedList =
		timeExerciseList.map { list ->
			list.groupBy {
				it.createdDate
			}
		}

	suspend fun getExercise(id: UUID) = exerciseEntryDao.queryExercise(id)


	suspend fun create(
		exercise: UUID,
		setNumber: Int,
		weight: Double,
		reps: Int,
	) {
		exerciseEntryDao.insertExercise(
			EntExerciseEntry(
				exerciseTypeId = exercise,
				setNumber = setNumber,
				weight = weight,
				reps = reps,
			)
		)
	}

	suspend fun import(
		createdDate: LocalDate,
		exerciseName: String,
		setNumber: Int,
		weight: Double,
		reps: Int,
	) {

		var exerciseType = exerciseTypeDao.queryByName(exerciseName)

		if (exerciseType == null) {
			exerciseType = EntExerciseType(
				name = exerciseName,
				usesUserWeight = false
			)

			exerciseTypeDao.insertExercise(exerciseType)
		}

		exerciseEntryDao.insertExercise(
			EntExerciseEntry(
				createdDate = createdDate,
				exerciseTypeId = exerciseType.id,
				setNumber = setNumber,
				weight = weight,
				reps = reps
			)
		)
	}

	suspend fun delete(id: UUID) {
		exerciseEntryDao.deleteExercise(exerciseEntryDao.queryExercise(id))
	}

	suspend fun update(
		exerciseID: UUID,
		exerciseTypeID: UUID,
		setNumber: Int,
		weight: Double,
		reps: Int
	) {
		val ent = exerciseEntryDao.queryExercise(exerciseID)
		exerciseEntryDao.updateExercise(
			ent.copy(
				exerciseTypeId = exerciseTypeID,
				setNumber = setNumber,
				weight = weight,
				reps = reps,
			)
		)
	}


}