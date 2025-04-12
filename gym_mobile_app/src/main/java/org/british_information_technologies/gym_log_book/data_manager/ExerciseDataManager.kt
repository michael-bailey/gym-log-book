package org.british_information_technologies.gym_log_book.data_manager

import org.british_information_technologies.gym_library.data_type.ExerciseItem
import org.british_information_technologies.gym_log_book.lib.data_manager.BaseDataManager
import org.british_information_technologies.gym_log_book.table.ExerciseTable
import javax.inject.Inject

@Deprecated("This is an old api, DON'T USE")
class ExerciseDataManager @Inject constructor(
	table: ExerciseTable
) : BaseDataManager<ExerciseItem>(table)