package net.michael_bailey.gym_log_book.client.exercise.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import net.michael_bailey.gym_log_book.client.exercise.service.ExerciseTypeService
import net.michael_bailey.gym_log_book.shared.exercise.model.EquipmentClass

class ExerciseTypeListViewModel(
	private val exerciseTypeService: ExerciseTypeService,
) : ViewModel() {

	init {
		println("Hello ExerciseTypeListViewModel")
	}

	val exerciseTypes = exerciseTypeService.exerciseTypes.map { it.toList() }


	fun addNewType(
		name: String,
		equipmentClass: EquipmentClass,
	) = viewModelScope.launch {
		exerciseTypeService.createNewType(
			name = name.trim(),
			equipmentClass = equipmentClass,
		)
	}

}
