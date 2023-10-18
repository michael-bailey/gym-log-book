package org.british_information_technologies.gym_log_book.data_manager

import org.british_information_technologies.gym_log_book.data_type.ExerciseItem
import org.british_information_technologies.gym_log_book.lib.data_manager.BaseDataManager
import org.british_information_technologies.gym_log_book.table.ExerciseTable
import javax.inject.Inject

class ExerciseDataManager @Inject constructor(
	table: ExerciseTable
) : BaseDataManager<ExerciseItem>(table)