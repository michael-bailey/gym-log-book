package net.michael_bailey.gym_log_book.client.platform

import androidx.room3.*
import kotlinx.serialization.json.Json
import net.michael_bailey.gym_log_book.client.exercise.entity.ExerciseEntryEntity
import net.michael_bailey.gym_log_book.client.exercise.entity.ExerciseTypeEntity
import net.michael_bailey.gym_log_book.client.exercise.entity.WeightEntryEntity
import net.michael_bailey.gym_log_book.client.exercise.repository.ExerciseEntryDao
import net.michael_bailey.gym_log_book.client.exercise.repository.ExerciseTypeDao
import net.michael_bailey.gym_log_book.client.exercise.repository.WeightDao
import net.michael_bailey.gym_log_book.shared.exercise.model.EquipmentClass
import kotlin.time.Instant

@Database(
	entities = [
		ExerciseEntryEntity::class,
		ExerciseTypeEntity::class,
		WeightEntryEntity::class,
	],
	version = 1
)
@ConstructedBy(AppDatabaseConstructor::class)
@ColumnTypeConverters(AppDatabase.Companion::class)
abstract class AppDatabase : RoomDatabase() {

	abstract fun exerciseEntryDao(): ExerciseEntryDao
	abstract fun exerciseTypeDao(): ExerciseTypeDao
	abstract fun weightEntryDao(): WeightDao

	companion object {
		@ColumnTypeConverter
		fun convertInstantToMilliSeconds(instant: Instant): Long = instant.toEpochMilliseconds()

		@ColumnTypeConverter
		fun convertMilliSecondsToInstant(milliseconds: Long): Instant = Instant.fromEpochMilliseconds(milliseconds)

		@ColumnTypeConverter
		fun convertEquipmentClassToJson(equipmentClass: EquipmentClass) =
			Json.encodeToString(EquipmentClass.serializer(), equipmentClass)

		@ColumnTypeConverter
		fun convertJsonToEquipmentClass(json: String) = Json.decodeFromString(EquipmentClass.serializer(), json)
	}
}

