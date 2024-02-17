package org.british_information_technologies.gym_log_book.database.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import org.british_information_technologies.gym_log_book.database.edge.EdgeGroupToType
import org.british_information_technologies.gym_log_book.database.entity.EntExerciseGroup
import org.british_information_technologies.gym_log_book.database.entity.EntExerciseType
import java.util.*

@Dao
/**
 * This is a data access object for exercise entries
 */
abstract class ExerciseTypeDao {

	data class ExerciseGroupAndExerciseEntries(
		@Embedded val type: EntExerciseType,
		@Relation(
			parentColumn = "id",
			entityColumn = "typeId",
			associateBy = Junction(EdgeGroupToType::class)
		)
		val groups: List<EntExerciseGroup>
	)

	@Query(
		"""
			SELECT * FROM exercise_types
		"""
	)
	abstract fun genQueryAll(): Flow<List<EntExerciseType>>

	@Query(
		"""
			SELECT * FROM exercise_types
			WHERE id == :id
		"""
	)
	abstract fun genQuery(id: UUID): Flow<EntExerciseType>

	@Query(
		"""
			SELECT * FROM exercise_types
			WHERE id == :id
		"""
	)
	abstract suspend fun queryExercise(id: UUID): EntExerciseType?

	@Query(
		"""
			SELECT * FROM exercise_types
			WHERE name == :name
			LIMIT 1
		"""
	)
	abstract fun genQueryByName(name: String): Flow<EntExerciseType?>

	@Query(
		"""
			SELECT * FROM exercise_types
			WHERE name == :name
			LIMIT 1
		"""
	)
	abstract suspend fun queryByName(name: String): EntExerciseType?

	@Query(
		"""
			SELECT COUNT(id)
			FROM exercise_types
		"""
	)
	abstract fun exerciseTypeCount(): Flow<Int>

	@Insert
	abstract suspend fun insertExercise(exerciseType: EntExerciseType)

	@Update
	abstract suspend fun updateExercise(exerciseType: EntExerciseType)

	@Delete
	abstract suspend fun deleteExercise(exerciseType: EntExerciseType)
}