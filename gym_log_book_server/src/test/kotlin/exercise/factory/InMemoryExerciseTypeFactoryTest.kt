@file:OptIn(ExperimentalUuidApi::class)

package exercise.factory

import net.michael_bailey.gym_log_book.server.exercise.domain.NewExerciseTypeModel
import net.michael_bailey.gym_log_book.server.exercise.factory.InMemoryExerciseTypeFactory
import net.michael_bailey.gym_log_book.shared.exercise.model.EquipmentClass
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class InMemoryExerciseTypeFactoryTest {

	private val factory = InMemoryExerciseTypeFactory()

	@Test
	fun `create maps model fields`() {
		val newExerciseType = NewExerciseTypeModel(PULL_UP_NAME, EquipmentClass.UserWeightMachine)

		val exerciseType = factory.create(newExerciseType)

		assertEquals(PULL_UP_NAME, exerciseType.name)
		assertEquals(EquipmentClass.UserWeightMachine, exerciseType.equipmentClass)
		assertNotEquals(Uuid.NIL, exerciseType.id)
	}

	@Test
	fun `create generates a new id for each entry`() {
		val newExerciseType = NewExerciseTypeModel(BENCH_PRESS_NAME, EquipmentClass.FreeWeight)

		val firstType = factory.create(newExerciseType)
		val secondType = factory.create(newExerciseType)

		assertNotEquals(firstType.id, secondType.id)
	}

	companion object {
		private const val PULL_UP_NAME = "Pull Up"
		private const val BENCH_PRESS_NAME = "Bench Press"
	}
}
