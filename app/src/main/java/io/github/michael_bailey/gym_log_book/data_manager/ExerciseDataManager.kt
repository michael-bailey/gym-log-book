package io.github.michael_bailey.gym_log_book.data_manager

import android.content.Context
import io.github.michael_bailey.gym_log_book.data_type.ExerciseItem
import io.github.michael_bailey.gym_log_book.lib.data_manager.BaseDataManager
import io.github.michael_bailey.gym_log_book.table.ExerciseTable

class ExerciseDataManager(
	private val context: Context,
	table: ExerciseTable = ExerciseTable(context)
) : BaseDataManager<ExerciseItem>(table)