package io.github.michael_bailey.gym_log_book.data_manager

import android.content.Context
import io.github.michael_bailey.gym_log_book.data_type.ExerciseType
import io.github.michael_bailey.gym_log_book.lib.data_manager.BaseDataManager
import io.github.michael_bailey.gym_log_book.table.ExerciseTypeTable

class ExerciseTypeDataManager(
	private val context: Context,
	table: ExerciseTypeTable = ExerciseTypeTable(context)
) : BaseDataManager<ExerciseType>(table)