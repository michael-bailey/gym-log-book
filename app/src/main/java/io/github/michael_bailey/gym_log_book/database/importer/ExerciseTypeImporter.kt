package io.github.michael_bailey.gym_log_book.database.importer

import io.github.michael_bailey.gym_log_book.app.App
import io.github.michael_bailey.gym_log_book.data_type.ExerciseType
import io.github.michael_bailey.gym_log_book.database.entity.EntExerciseType
import io.github.michael_bailey.gym_log_book.lib.data_manager.BaseDataManager

class ExerciseTypeImporter(
	application: App
) : AbstractImporter<ExerciseType, EntExerciseType>(
	application
) {

	override fun getManager(): BaseDataManager<ExerciseType> =
		application.exerciseTypeDataManager

	override fun getDao(): ExportDao<EntExerciseType> =
		application.db.exerciseTypeDao()

	override fun migrate(inputItem: ExerciseType): EntExerciseType =
		EntExerciseType(
			name = inputItem.name,
			usesUserWeight = inputItem.isUsingUserWeight,
		)

	override fun applySorting(inputData: List<ExerciseType>): List<ExerciseType> =
		inputData.sortedBy { it.name }

}