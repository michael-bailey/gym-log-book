@file:OptIn(ExperimentalUuidApi::class)

package exercise

import net.michael_bailey.gym_log_book.shared.exercise.command.NewExerciseEntryCommand
import net.michael_bailey.gym_log_book.shared.exercise.command.NewExerciseTypeCommand
import net.michael_bailey.gym_log_book.shared.exercise.model.EquipmentClass
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.time.Instant
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class ExerciseCommandValidationTest {

	@Test
	fun `NewExerciseEntryCommand accepts valid values`() {
		val command = createEntryCommand()

		assertEquals(SET_NUMBER, command.setNumber)
		assertEquals(WEIGHT, command.weight)
		assertEquals(REPS, command.reps)
	}

	@Test
	fun `NewExerciseEntryCommand rejects nil exerciseTypeId`() {
		assertFailsWith<IllegalArgumentException> {
			createEntryCommand(exerciseTypeId = Uuid.NIL)
		}
	}

	@Test
	fun `NewExerciseEntryCommand rejects non positive set number`() {
		assertFailsWith<IllegalArgumentException> {
			createEntryCommand(setNumber = 0)
		}
	}

	@Test
	fun `NewExerciseEntryCommand rejects non finite weight`() {
		assertFailsWith<IllegalArgumentException> {
			createEntryCommand(weight = Double.POSITIVE_INFINITY)
		}
	}

	@Test
	fun `NewExerciseEntryCommand rejects negative weight`() {
		assertFailsWith<IllegalArgumentException> {
			createEntryCommand(weight = -1.0)
		}
	}

	@Test
	fun `NewExerciseEntryCommand rejects non positive reps`() {
		assertFailsWith<IllegalArgumentException> {
			createEntryCommand(reps = 0)
		}
	}

	@Test
	fun `NewExerciseTypeCommand accepts valid values`() {
		val command = NewExerciseTypeCommand("Pull Up", EquipmentClass.UserWeightMachine)

		assertEquals("Pull Up", command.name)
		assertEquals(EquipmentClass.UserWeightMachine, command.equipmentClass)
	}

	@Test
	fun `NewExerciseTypeCommand rejects blank name`() {
		assertFailsWith<IllegalArgumentException> {
			NewExerciseTypeCommand("   ", EquipmentClass.Machine)
		}
	}

	private fun createEntryCommand(
		exerciseTypeId: Uuid = Uuid.random(),
		setNumber: Int = SET_NUMBER,
		weight: Double = WEIGHT,
		reps: Int = REPS,
	): NewExerciseEntryCommand = NewExerciseEntryCommand(
		exerciseTypeId = exerciseTypeId,
		creationInstant = FIXED_INSTANT,
		setNumber = setNumber,
		weight = weight,
		reps = reps,
	)

	companion object {
		private val FIXED_INSTANT = Instant.parse("2025-01-02T03:04:05Z")
		private const val SET_NUMBER = 3
		private const val WEIGHT = 185.5
		private const val REPS = 8
	}
}
