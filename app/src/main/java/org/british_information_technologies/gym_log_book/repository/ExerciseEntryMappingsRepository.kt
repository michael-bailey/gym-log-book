package org.british_information_technologies.gym_log_book.repository

import kotlinx.coroutines.flow.map
import org.british_information_technologies.gym_log_book.database.entity.EntExerciseEntry
import org.british_information_technologies.gym_log_book.lib.PeriodGroup
import java.time.LocalDate
import java.time.LocalDateTime
import javax.inject.Inject

class ExerciseEntryMappingsRepository @Inject constructor(
	exerciseEntryRepository: ExerciseEntryRepository
) {

	private val timeExerciseGroupedList =
		exerciseEntryRepository.timeExerciseList.map { list ->
			list.groupBy {
				it.createdDate
			}
		}

	val exercisesGroupedByTime = timeExerciseGroupedList.map {
		val output = mutableMapOf<String, List<EntExerciseEntry>>()
		val sortedList = it.toList().sortedByDescending { it.first }.toMap()
		for (date in sortedList.keys) {
			val group =
				PeriodGroup.getPeriodGroup(period = date.until(LocalDate.now()))
					.toString()
			val list = output[group]
			if (list == null) {
				output[group] =
					sortedList[date]!!.toList().sortedByDescending { it ->
						LocalDateTime.of(
							it.createdDate,
							it.createdTime
						)
					}
			} else {
				output[group] =
					(list + sortedList[date]!!.toList()).sortedByDescending { it ->
						LocalDateTime.of(
							it.createdDate,
							it.createdTime
						)
					}
			}
		}
		output
	}
}