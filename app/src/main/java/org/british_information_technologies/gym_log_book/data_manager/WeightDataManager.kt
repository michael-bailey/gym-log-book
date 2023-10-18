package org.british_information_technologies.gym_log_book.data_manager

import android.content.Context
import org.british_information_technologies.gym_log_book.data_type.WeightItem
import org.british_information_technologies.gym_log_book.lib.data_manager.BaseDataManager
import org.british_information_technologies.gym_log_book.table.WeightTable
import javax.inject.Inject

class WeightDataManager @Inject constructor(
	private val context: Context,
	table: WeightTable
) : BaseDataManager<WeightItem>(table)