package io.github.michael_bailey.gym_log_book.data_manager

import io.github.michael_bailey.gym_log_book.data_type.ExerciseItem
import io.github.michael_bailey.gym_log_book.lib.data_manager.BaseDataManager
import io.github.michael_bailey.gym_log_book.table.ExerciseTable
import javax.inject.Inject

class ExerciseDataManager @Inject constructor(
	table: ExerciseTable
) : BaseDataManager<ExerciseItem>(table)