package net.michael_bailey.gym_log_book.client.window.developer

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Category
import androidx.compose.material.icons.rounded.ListAlt
import androidx.compose.material.icons.rounded.Numbers
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import net.michael_bailey.gym_log_book.client.component.navigation.GymNavItem

class DeveloperWindowViewModel : ViewModel() {

	enum class DevTab(
		val icon: ImageVector,
		val label: String,
	) {
		Counter(icon = Icons.Rounded.Numbers, label = "Counter"),
		Type(icon = Icons.Rounded.Category, label = "Types"),
		Entry(icon = Icons.Rounded.ListAlt, label = "Exercises")
	}

	private val _activeTabs = MutableStateFlow<List<GymNavItem<DevTab>>>(initActiveTabs())

	val activeTabs: Flow<List<GymNavItem<DevTab>>> = _activeTabs

	companion object {
		fun initActiveTabs(): List<GymNavItem<DevTab>> = DevTab.entries.map {
			GymNavItem(
				icon = it.icon,
				label = it.label,
				route = it
			)
		}
	}
}