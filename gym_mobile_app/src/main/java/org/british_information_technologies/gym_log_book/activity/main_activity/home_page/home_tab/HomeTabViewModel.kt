package org.british_information_technologies.gym_log_book.activity.main_activity.home_page.home_tab

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import org.british_information_technologies.gym_log_book.repository.ExerciseEntryRepository
import org.british_information_technologies.gym_log_book.repository.ExerciseTypeRepository
import java.time.LocalDate
import java.time.Period
import javax.inject.Inject

@HiltViewModel
class HomeTabViewModel @Inject constructor(
	private val exerciseRepository: ExerciseEntryRepository,
	private val exerciseTypeRepository: ExerciseTypeRepository,
) : ViewModel() {

	val lastGymVisit = exerciseRepository.exercises.map {
		it.first()
	}.map {
		Period.between(it.createdDate, LocalDate.now())
	}.map {
		when {
			it.years > 0 -> "${it.years} Years ago"
			it.months > 0 -> "${it.months} Months ago"
			it.days > 7 -> "${it.days / 7} Weeks ago"
			it.days > 1 -> "${it.days} Days ago"

			else -> "today"
		}
	}.asLiveData()

	val _lastSessionsExercises = exerciseRepository.exercises

	val lastSessionsExercises: LiveData<List<String>?> =
		_lastSessionsExercises.map { list ->
			list.groupBy { item ->
				item.createdDate
			}
		}.map { map ->
			val lastSession = map.keys.sorted().find {
				it < LocalDate.now()
			}

			if (lastSession == null) {
				return@map null
			}

			map[lastSession]
		}.map { sessions ->
			sessions?.map { item ->
				item.exerciseTypeId
			}?.toSet()
		}.combine(exerciseTypeRepository.exerciseTypes) { ids, types ->
			ids?.map { id ->
				types.find { it.id == id }?.name
			}?.filterNotNull()
		}.asLiveData()

}