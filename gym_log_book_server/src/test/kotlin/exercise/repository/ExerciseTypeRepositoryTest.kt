@file:OptIn(ExperimentalUuidApi::class, ExperimentalCoroutinesApi::class)

package exercise.repository

import RepositoryUnitTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import net.michael_bailey.gym_log_book.server.database.schema.v1.ExerciseTypeTable
import net.michael_bailey.gym_log_book.server.exercise.domain.NewExerciseTypeModel
import net.michael_bailey.gym_log_book.server.exercise.mapper.toExerciseTypeDomain
import net.michael_bailey.gym_log_book.server.exercise.repository.ExerciseTypeRepository
import net.michael_bailey.gym_log_book.shared.exercise.model.EquipmentClass
import org.jetbrains.exposed.v1.core.Table
import org.jetbrains.exposed.v1.jdbc.insertAndGetId
import org.jetbrains.exposed.v1.jdbc.selectAll
import kotlin.test.Test
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class ExerciseTypeRepositoryTest : RepositoryUnitTest() {

	override val tables: Array<Table> = arrayOf(ExerciseTypeTable)

	@Test
	fun `getting existing type, by id, returns existing type`() = withTestDatabase {

		val id = ExerciseTypeTable.insertAndGetId {
			it[this.name] = EXERCISE_NAME
			it[this.equipmentClass] = EQUIPMENT_CLASS::class.simpleName!!
			it[this.equipmentClassText] = null
		}.value

		val underTest = ExerciseTypeRepository(database = db)

		val newType = underTest.getExerciseType(id)!!

		assert(newType.equipmentClass == EQUIPMENT_CLASS)
		assert(newType.name == EXERCISE_NAME)
	}

	@Test
	fun `getting non existing type, by id, returns null`() = withTestDatabase {

		val id = Uuid.NIL

		val underTest = ExerciseTypeRepository(database = db)

		val newType = underTest.getExerciseType(id)

		assert(newType == null)
	}

	@Test
	fun `getting existing type, by name, returns existing type`() = withTestDatabase {

		ExerciseTypeTable.insertAndGetId {
			it[this.name] = EXERCISE_NAME
			it[this.equipmentClass] = EQUIPMENT_CLASS::class.simpleName!!
			it[this.equipmentClassText] = null
		}

		val underTest = ExerciseTypeRepository(database = db)

		val newType = underTest.getExerciseTypeByName(EXERCISE_NAME)!!

		assert(newType.equipmentClass == EQUIPMENT_CLASS)
		assert(newType.name == EXERCISE_NAME)
	}

	@Test
	fun `getting non existing type, by name, returns null`() = withTestDatabase {
		val underTest = ExerciseTypeRepository(database = db)

		val newType = underTest.getExerciseTypeByName(EXERCISE_NAME)

		assert(newType == null)
	}

	@Test
	fun `allExerciseTypes emits inserted items on startup`() = withTestDatabase {
		ExerciseTypeTable.insertAndGetId {
			it[this.name] = EXERCISE_NAME
			it[this.equipmentClass] = EQUIPMENT_CLASS::class.simpleName!!
			it[this.equipmentClassText] = null
		}
		val expected = ExerciseTypeTable.selectAll().first().toExerciseTypeDomain()

		val underTest = ExerciseTypeRepository(database = db)

		val types = underTest.allExerciseTypes.first()

		assert(types.size == 1)
		assert(types.first() == expected)
	}

	@Test
	fun `observeExerciseType emits existing type by id`() = withTestDatabase {
		val id = ExerciseTypeTable.insertAndGetId {
			it[this.name] = EXERCISE_NAME
			it[this.equipmentClass] = EQUIPMENT_CLASS::class.simpleName!!
			it[this.equipmentClassText] = null
		}.value

		val underTest = ExerciseTypeRepository(database = db)

		val observed = underTest.observeExerciseType(id).first()

		assert(observed == ExerciseTypeTable.selectAll().first().toExerciseTypeDomain())
	}

	@Test
	fun `observeExerciseType emits null for missing id`() = withTestDatabase {
		val underTest = ExerciseTypeRepository(database = db)

		val observed = underTest.observeExerciseType(Uuid.NIL).first()

		assert(observed == null)
	}

	@Test
	fun `create inserts a new exerciseType`() = withTestDatabase {

		val underTest = ExerciseTypeRepository(database = db)

		underTest.createType(
			newExerciseType = NewExerciseTypeModel(
				name = EXERCISE_NAME,
				equipmentClass = EQUIPMENT_CLASS
			)
		)

		val newType = ExerciseTypeTable.selectAll().first().toExerciseTypeDomain()

		assert(newType.equipmentClass == EQUIPMENT_CLASS)
		assert(newType.name == EXERCISE_NAME)
	}

	companion object {
		const val EXERCISE_NAME = "new Type"
		val EQUIPMENT_CLASS = EquipmentClass.Machine
	}
}
