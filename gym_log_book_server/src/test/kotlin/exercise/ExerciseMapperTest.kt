@file:OptIn(ExperimentalUuidApi::class)

package exercise

import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import net.michael_bailey.gym_log_book.server.exercise.domain.ExerciseEntryModel
import net.michael_bailey.gym_log_book.server.exercise.domain.ExerciseTypeModel
import net.michael_bailey.gym_log_book.server.exercise.mapper.toDomain
import net.michael_bailey.gym_log_book.server.exercise.mapper.toShared
import net.michael_bailey.gym_log_book.server.exercise.mapper.toView
import net.michael_bailey.gym_log_book.shared.exercise.command.NewExerciseEntryCommand
import net.michael_bailey.gym_log_book.shared.exercise.command.NewExerciseTypeCommand
import net.michael_bailey.gym_log_book.shared.exercise.model.EquipmentClass
import net.michael_bailey.gym_log_book.shared.exercise.model.ExerciseEntry
import net.michael_bailey.gym_log_book.shared.exercise.model.ExerciseEntryView
import net.michael_bailey.gym_log_book.shared.exercise.model.ExerciseType
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.time.Instant
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class ExerciseMapperTest {

	@Test
	fun `NewExerciseEntryCommand maps to domain model`() {
		val command = NewExerciseEntryCommand(
			exerciseTypeId = Uuid.random(),
			creationInstant = FIXED_INSTANT,
			setNumber = SET_NUMBER,
			weight = WEIGHT,
			reps = REPS,
		)

		assertEquals(
			net.michael_bailey.gym_log_book.server.exercise.domain.NewExerciseEntryModel(
				exerciseTypeId = command.exerciseTypeId,
				creationInstant = FIXED_INSTANT,
				setNumber = SET_NUMBER,
				weight = WEIGHT,
				reps = REPS,
			),
			command.toDomain(),
		)
	}

	@Test
	fun `ExerciseEntryModel maps to shared core entry`() {
		val model = createEntryModel()

		assertEquals(
			ExerciseEntry(
				id = model.id,
				date = FIXED_INSTANT.toLocalDateTime(TimeZone.UTC),
				exerciseTypeId = model.exerciseTypeId,
				setNumber = SET_NUMBER,
				weight = WEIGHT,
				reps = REPS,
			),
			model.toShared(),
		)
	}

	@Test
	fun `ExerciseEntryModel maps to read model view`() {
		val model = createEntryModel()

		assertEquals(
			ExerciseEntryView(
				id = model.id,
				date = FIXED_INSTANT.toLocalDateTime(TimeZone.UTC),
				exerciseTypeId = model.exerciseTypeId,
				exerciseTypeName = "Bench Press",
				setNumber = SET_NUMBER,
				weight = WEIGHT,
				reps = REPS,
			),
			model.toView("Bench Press"),
		)
	}

	@Test
	fun `NewExerciseTypeCommand trims name when mapping to domain model`() {
		val command = NewExerciseTypeCommand("  Pull Up  ", EquipmentClass.UserWeightMachine)

		assertEquals(
			net.michael_bailey.gym_log_book.server.exercise.domain.NewExerciseTypeModel(
				name = "Pull Up",
				equipmentClass = EquipmentClass.UserWeightMachine,
			),
			command.toDomain(),
		)
	}

	@Test
	fun `ExerciseTypeModel maps to shared model`() {
		val model = ExerciseTypeModel(
			id = Uuid.random(),
			name = "Bench Press",
			equipmentClass = EquipmentClass.Machine,
		)

		assertEquals(
			ExerciseType(
				id = model.id,
				name = "Bench Press",
				equipmentClass = EquipmentClass.Machine,
			),
			model.toShared(),
		)
	}

	private fun createEntryModel(): ExerciseEntryModel = ExerciseEntryModel(
		id = Uuid.random(),
		exerciseTypeId = Uuid.random(),
		creationInstant = FIXED_INSTANT,
		setNumber = SET_NUMBER,
		weight = WEIGHT,
		reps = REPS,
	)

	companion object {
		private val FIXED_INSTANT = Instant.parse("2025-01-02T03:04:05Z")
		private const val SET_NUMBER = 3
		private const val WEIGHT = 185.5
		private const val REPS = 8
	}
}
