package org.british_information_technologies.gym_log_book.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Embedded
import androidx.room.Insert
import androidx.room.Junction
import androidx.room.Query
import androidx.room.Relation
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow
import org.british_information_technologies.gym_log_book.database.edge.EdgeGroupToType
import org.british_information_technologies.gym_log_book.database.entity.EntExerciseGroup
import org.british_information_technologies.gym_log_book.database.entity.EntExerciseType
import java.util.UUID

@Dao
abstract class ExerciseGroupDao {

	data class ExerciseGroupAndExerciseEntries(
		@Embedded val group: EntExerciseGroup,
		@Relation(
			parentColumn = "id",
			entityColumn = "id",
			entity = EntExerciseType::class,
			associateBy = Junction(
				value = EdgeGroupToType::class,
				parentColumn = "groupId",
				entityColumn = "typeId",
			)
		)
		val entries: List<EntExerciseType>
	)

	@Query(
		"""
			SELECT * FROM exercise_groups
		"""
	)
	abstract fun genQueryAll(): Flow<List<EntExerciseGroup>>

	@Query(
		"""
			SELECT * FROM exercise_groups
		"""
	)
	@Transaction
	abstract fun genQueryAllWithExerciseEntries(): Flow<List<ExerciseGroupAndExerciseEntries>>

	@Query(
		"""
			SELECT * FROM exercise_groups
			where id = :id
		"""
	)
	abstract fun genQuery(id: UUID): Flow<EntExerciseGroup>

	@Query(
		"""
			SELECT * FROM exercise_groups
			where id = :id
		"""
	)
	@Transaction
	abstract fun genQueryWithExerciseEntries(id: UUID): Flow<ExerciseGroupAndExerciseEntries>

	@Insert
	abstract suspend fun insertGroup(group: EntExerciseGroup)

	@Delete
	abstract suspend fun deleteGroup(group: EntExerciseGroup)
}