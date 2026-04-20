@file:OptIn(ExperimentalUuidApi::class)

package exercise.factory

import net.michael_bailey.gym_log_book.server.exercise.factory.InMemoryExerciseTypeFactory
import net.michael_bailey.gym_log_book.shared.exercise.model.EquipmentClass
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.test.assertTrue
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class InMemoryExerciseTypeFactoryTest {

	private val factory = InMemoryExerciseTypeFactory()

	@Test
	fun `create sets user weight flag for UsesUserWeight equipment`() {
		val exerciseType = factory.create(PULL_UP_NAME, EquipmentClass.UsesUserWeight)

		assertEquals(PULL_UP_NAME, exerciseType.name)
		assertEquals(EquipmentClass.UsesUserWeight, exerciseType.equipmentClass)
		assertTrue(exerciseType.isUsingUserWeight)
		assertNotEquals(Uuid.NIL, exerciseType.id)
	}

	@Test
	fun `create leaves user weight flag false for non user weight equipment`() {
		val exerciseType = factory.create(BENCH_PRESS_NAME, EquipmentClass.FreeWeight)

		assertEquals(BENCH_PRESS_NAME, exerciseType.name)
		assertEquals(EquipmentClass.FreeWeight, exerciseType.equipmentClass)
		assertEquals(false, exerciseType.isUsingUserWeight)
		assertNotEquals(Uuid.NIL, exerciseType.id)
	}

	companion object {
		private const val PULL_UP_NAME = "Pull Up"
		private const val BENCH_PRESS_NAME = "Bench Press"
	}
}
