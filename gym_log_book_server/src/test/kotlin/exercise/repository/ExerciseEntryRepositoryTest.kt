@file:OptIn(ExperimentalUuidApi::class)

package exercise.repository

import RepositoryUnitTest
import io.mockk.every
import io.mockk.mockk
import net.michael_bailey.gym_log_book.server.database.schema.v1.ExerciseEntryTable
import net.michael_bailey.gym_log_book.server.database.schema.v1.ExerciseTypeTable
import net.michael_bailey.gym_log_book.server.exercise.domain.NewExerciseEntryModel
import net.michael_bailey.gym_log_book.server.exercise.repository.ExerciseEntryRepository
import net.michael_bailey.gym_log_book.shared.exercise.model.EquipmentClass
import org.jetbrains.exposed.v1.core.Table
import org.jetbrains.exposed.v1.jdbc.insertAndGetId
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.time.Clock
import kotlin.time.Instant
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class ExerciseEntryRepositoryTest : RepositoryUnitTest() {

	override val tables: Array<Table>
		get() = arrayOf(
			ExerciseTypeTable,
			ExerciseEntryTable
		)

	val clock: Clock = mockk {
		every { now() } returns createTime
	}

	@Test
	fun `create inserts new entry into table`() = withTestDatabase {

		val typeId = createExerciseType()

		val underTest = ExerciseEntryRepository(this.db)

		val model = underTest.createEntry(
			newExerciseEntry = NewExerciseEntryModel(
				exerciseTypeId = typeId,
				creationInstant = clock.now(),
				setNumber = SET_NUMBER,
				weight = WEIGHT,
				reps = REPS,
			)
		)

		assertEquals(createTime, model.creationInstant)
		assertEquals(SET_NUMBER, model.setNumber)
		assertEquals(WEIGHT, model.weight)
		assertEquals(REPS, model.reps)
	}

	private fun createExerciseType(): Uuid = ExerciseTypeTable.insertAndGetId {
		it[this.name] = "Test Type"
		it[this.equipmentClass] = EquipmentClass.FreeWeight.toString()
	}.value

	companion object {
		val createTime = Instant.fromEpochSeconds(1000000000)
		const val SET_NUMBER = 1
		const val WEIGHT = 12.0
		const val REPS = 12
	}

}