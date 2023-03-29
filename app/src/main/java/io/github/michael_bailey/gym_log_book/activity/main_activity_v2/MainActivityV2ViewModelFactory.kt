package io.github.michael_bailey.gym_log_book.activity.main_activity_v2

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import io.github.michael_bailey.gym_log_book.App

class MainActivityV2ViewModelFactory(
	private val application: App,
) : ViewModelProvider.NewInstanceFactory() {

	override fun <T : ViewModel> create(modelClass: Class<T>): T {
		val a = MainActivityV2ViewModel(application) as T
		return a
	}
}