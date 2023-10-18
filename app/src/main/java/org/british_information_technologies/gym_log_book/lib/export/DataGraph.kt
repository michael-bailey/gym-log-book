package org.british_information_technologies.gym_log_book.lib.export

import com.google.gson.Gson
import org.british_information_technologies.gym_log_book.database.dao.ExerciseEntryDao
import org.british_information_technologies.gym_log_book.database.dao.ExerciseTypeDao
import org.british_information_technologies.gym_log_book.database.dao.WeightEntryDao
import javax.inject.Inject


class DataGraph {

	@Inject
	lateinit var entryDao: ExerciseEntryDao

	@Inject
	lateinit var typeDao: ExerciseTypeDao

	@Inject
	lateinit var weightDao: WeightEntryDao


	val exerciseEntries by lazy { entryDao.getAllExercise() }
	val exerciseTypes by lazy { entryDao.getAllExercise() }
	val weightEntries by lazy { entryDao.getAllExercise() }

	companion object {
		fun fromJSON(string: String): DataGraph =
			Gson().fromJson(string, DataGraph::class.java)
	}

	fun toJSON(): String = Gson().toJson(this)
}