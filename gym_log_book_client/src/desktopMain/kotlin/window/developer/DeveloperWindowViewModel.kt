package net.michael_bailey.gym_log_book.client.window.developer

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.material.icons.sharp.LockPerson
import androidx.compose.material.icons.sharp.Numbers
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import net.michael_bailey.gym_log_book.client.authentication.service.OldAuthenticationService
import net.michael_bailey.gym_log_book.client.component.navigation.GymNavItem

class DeveloperWindowViewModel(
	private val authenticator: OldAuthenticationService,
) : ViewModel() {

	enum class DevTab(
		val icon: ImageVector,
		val label: String,
	) {
		Counter(icon = Icons.Sharp.Numbers, label = "* Counter"),
		Login(icon = Icons.Sharp.LockPerson, label = "* Login"),

		Network(icon = Icons.Rounded.NetworkPing, "Network"),
		NewLogin(icon = Icons.Rounded.Password, label = "Login"),
		NewAuth(icon = Icons.Rounded.LockPerson, label = "Auth"),

		Type(icon = Icons.Rounded.Category, label = "Types"),
		Entry(icon = Icons.Rounded.FilterList, label = "Exercises"),
	}

	private val _activeTabs = authenticator.isAuthenticated
		.map(::mapFilterDestinations)
		.map(::mapDestinations)

	val activeTabs: Flow<List<GymNavItem<DevTab>>> = _activeTabs

	private fun mapFilterDestinations(
		isOldLoggedIn: Boolean,
	): List<DevTab> = listOf(
		DevTab.Network,
		DevTab.Counter,
		DevTab.Login,
		DevTab.NewLogin,
		DevTab.NewAuth,
	) + if (isOldLoggedIn) {
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