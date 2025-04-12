package org.british_information_technologies.gym_log_book.extension.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

fun ViewModel.launch(run: suspend () -> Unit) {
	viewModelScope.launch(Dispatchers.IO) { run() }
}