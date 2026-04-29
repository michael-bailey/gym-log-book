package net.michael_bailey.gym_log_book.client.home

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import net.michael_bailey.gym_log_book.client.config.Strings

class HomePageViewModel : ViewModel() {

	enum class HomePageTab {
		ExerciseEntryTab,
		ExerciseTypeTab,
	}

	data class HomePageDestination(
		val tab: HomePageTab,
		val title: String,
		val navigationLabel: String,
		val fabLabel: String
	)

	data class UiState(
		val selectedTab: HomePageTab = HomePageTab.ExerciseEntryTab,
		val isExerciseGuideVisible: Boolean = false,
		val isExerciseTypeFormVisible: Boolean = false,
	)

	private val _uiState = MutableStateFlow(UiState())
	val uiState: StateFlow<UiState> = _uiState.asStateFlow()

	fun setPageTab(tab: HomePageTab) {
		_uiState.value = _uiState.value.copy(
			selectedTab = tab,
			isExerciseGuideVisible = false,
			isExerciseTypeFormVisible = false,
		)
	}

	fun onFabClick() {
		when (_uiState.value.selectedTab) {
			HomePageTab.ExerciseEntryTab -> startExerciseGuide()
			HomePageTab.ExerciseTypeTab -> openExerciseTypeForm()
		}
	}

	fun startExerciseGuide() {
		_uiState.value = _uiState.value.copy(isExerciseGuideVisible = true)
	}

	fun dismissExerciseGuide() {
		_uiState.value = _uiState.value.copy(isExerciseGuideVisible = false)
	}

	fun openExerciseTypeForm() {
		_uiState.value = _uiState.value.copy(isExerciseTypeFormVisible = true)
	}

	fun dismissExerciseTypeForm() {
		_uiState.value = _uiState.value.copy(isExerciseTypeFormVisible = false)
	}

	companion object {
		val destinations = listOf(
			HomePageDestination(
				tab = HomePageTab.ExerciseEntryTab,
				title = Strings.HOME_PAGE_EXERCISE_ENTRIES_TITLE,
				navigationLabel = Strings.HOME_PAGE_ENTRY_NAV_BUTTON_LABEL,
				fabLabel = Strings.START_EXERCISE_GUIDE_FAB,
			),
			HomePageDestination(
				tab = HomePageTab.ExerciseTypeTab,
				title = Strings.HOME_PAGE_EXERCISE_TYPES_TITLE,
				navigationLabel = Strings.HOME_PAGE_TYPE_NAV_BUTTON_LABEL,
				fabLabel = Strings.ADD_EXERCISE_TYPE_FAB,
			),
		)
	}
}
