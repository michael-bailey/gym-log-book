package io.github.michael_bailey.gym_log_book.data_manager

import android.content.Context
import io.github.michael_bailey.gym_log_book.data_type.WeightItem
import io.github.michael_bailey.gym_log_book.lib.data_manager.BaseDataManager
import io.github.michael_bailey.gym_log_book.table.WeightTable

class WeightDataManager(
	private val context: Context,
	table: WeightTable = WeightTable(context)
) : BaseDataManager<WeightItem>(table)