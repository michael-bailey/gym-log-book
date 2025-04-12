package org.british_information_technologies.gym_log_book.data_manager

import android.content.Context
import org.british_information_technologies.gym_library.data_type.ExerciseType
import org.british_information_technologies.gym_log_book.lib.data_manager.BaseDataManager
import org.british_information_technologies.gym_log_book.table.ExerciseTypeTable
import javax.inject.Inject

@Deprecated("This is an old api, DON'T USE")
class ExerciseTypeDataManager @Inject constructor(
	private val context: Context,
	table: ExerciseTypeTable
) : BaseDataManager<ExerciseType>(table)