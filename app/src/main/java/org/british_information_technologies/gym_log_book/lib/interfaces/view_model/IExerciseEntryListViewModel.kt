package org.british_information_technologies.gym_log_book.lib.interfaces.view_model

import androidx.compose.foundation.lazy.LazyListState
import androidx.lifecycle.LiveData
import org.british_information_technologies.gym_log_book.database.entity.EntExerciseType
import java.util.UUID

interface IExerciseEntryListViewModel : IExerciseEntryViewModel {
	val exerciseTypeList: LiveData<List<EntExerciseType>>
	val isExercisesEmpty: LiveData<Boolean>
	val timeExerciseGroupedList: LiveData<Map<String, List<UUID>>>
	val exerciseListState: LazyListState
}