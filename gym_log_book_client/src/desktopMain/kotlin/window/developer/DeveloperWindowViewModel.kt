package net.michael_bailey.gym_log_book.client.window.developer

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Category
import androidx.compose.material.icons.rounded.FilterList
import androidx.compose.material.icons.rounded.LockPerson
import androidx.compose.material.icons.rounded.Numbers
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import net.michael_bailey.gym_log_book.client.authentication.service.AuthenticationService
import net.michael_bailey.gym_log_book.client.component.navigation.GymNavItem

class DeveloperWindowViewModel(
	private val authenticator: AuthenticationService
) : ViewModel() {

	enum class DevTab(
		val icon: ImageVector,
		val label: String,
	) {
		Counter(icon = Icons.Rounded.Numbers, label = "Counter"),
		Login(icon = Icons.Rounded.LockPerson, label = "Login"),
		Type(icon = Icons.Rounded.Category, label = "Types"),
		Entry(icon = Icons.Rounded.FilterList, label = "Exercises")
	}

	private val _activeTabs = authenticator.isAuthenticated
		.map(::mapFilterDestinations)
		.map(::mapDestinations)

	val activeTabs: Flow<List<GymNavItem<DevTab>>> = _activeTabs

	private fun mapFilterDestinations(
		isLoggedIn: Boolean
	): List<DevTab> = listOf(DevTab.Counter, DevTab.Login) + if (isLoggedIn) {
		listOf(DevTab.Type, DevTab.Entry)
	} else {
		emptyList()
	}

	private fun mapDestinations(
		destinations: List<DevTab>
	): List<GymNavItem<DevTab>> = destinations.map(::mapTabToRoute)

	private fun mapTabToRoute(tab: DevTab): GymNavItem<DevTab> = GymNavItem(
		icon = tab.icon,
		label = tab.label,
		route = tab
	)
}