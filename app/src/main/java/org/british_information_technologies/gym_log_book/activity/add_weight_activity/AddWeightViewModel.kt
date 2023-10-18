package org.british_information_technologies.gym_log_book.activity.add_weight_activity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import org.british_information_technologies.gym_log_book.lib.validation.Validator
import org.british_information_technologies.gym_log_book.repository.WeightEntryRepository
import javax.inject.Inject

@HiltViewModel
class AddWeightViewModel @Inject constructor(
	private val weightEntryRepository: WeightEntryRepository
) : ViewModel() {

	private val currentWeight = MutableStateFlow("")

	val weight = currentWeight.asLiveData()

	val isSubmitEnabled = currentWeight.map {
		Validator.FloatValidator().validator(it).isSuccess
	}.asLiveData()


	fun setWeight(weight: String) = viewModelScope.launch {
		currentWeight.emit(weight)
	}

	fun finalise() = viewModelScope.launch {
		weightEntryRepository.create(weight = currentWeight.value.toDouble())
	}
}