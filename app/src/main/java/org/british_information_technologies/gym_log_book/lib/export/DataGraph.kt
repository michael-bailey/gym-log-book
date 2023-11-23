package org.british_information_technologies.gym_log_book.lib.export

import com.google.gson.Gson
import org.british_information_technologies.gym_log_book.database.entity.EntExerciseEntry
import org.british_information_technologies.gym_log_book.database.entity.EntExerciseType
import org.british_information_technologies.gym_log_book.database.entity.EntWeightEntry


class DataGraph(
	internal val exerciseEntries: List<EntExerciseEntry>,
	internal val exerciseTypes: List<EntExerciseType>,
	internal val weightEntries: List<EntWeightEntry>,
) {

	companion object {
		fun fromJSON(string: String): DataGraph =
			Gson().fromJson(string, DataGraph::class.java)
	}

	fun toJSON(): String = Gson().toJson(this)
}