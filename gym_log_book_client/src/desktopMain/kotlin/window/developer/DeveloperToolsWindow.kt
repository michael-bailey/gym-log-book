package net.michael_bailey.gym_log_book.client.window.developer

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Window
import net.michael_bailey.gym_log_book.client.component.navigation.GymAdaptiveScaffold
import net.michael_bailey.gym_log_book.client.component.navigation.GymNavItem
import net.michael_bailey.gym_log_book.client.component.navigation.GymNavScope
import net.michael_bailey.gym_log_book.client.window.developer.DeveloperWindowViewModel.DevTab
import net.michael_bailey.gym_log_book.client.window.developer.authentication_controller.AuthenticationControllerPage
import net.michael_bailey.gym_log_book.client.window.developer.counter.DevCounterTabPage
import net.michael_bailey.gym_log_book.client.window.developer.entry.DevExerciseEntryTabPage
import net.michael_bailey.gym_log_book.client.window.developer.login_controller.LoginControllerPage
import net.michael_bailey.gym_log_book.client.window.developer.network.NetworkPage
import net.michael_bailey.gym_log_book.client.window.developer.type.DevExerciseTypeTabPage
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun DeveloperToolWindow(
	viewModel: DeveloperWindowViewModel = koinViewModel()
) {

	var currentTab by remember { mutableStateOf(DevTab.Counter) }

	val tabs: List<GymNavItem<DevTab>> by viewModel.activeTabs.collectAsState(
		initial = emptyList()
	)

	val navContent: GymNavScope<DevTab>.() -> Unit = {
		tabs.forEach {
			item(it)
		}
	}

	LaunchedEffect(tabs, currentTab) {
		if (tabs.none { it.route == currentTab }) {
			currentTab = tabs.firstOrNull()?.route ?: DevTab.Counter
		}
	}

	Window(
		onCloseRequest = {},
	) {
		GymAdaptiveScaffold(
			currentRoute = currentTab,
			onNavigate = { currentTab = it },
			navContent = navContent,
		) { paddingValues ->
			Box(
				modifier = Modifier.padding(paddingValues).fillMaxSize(),
				contentAlignment = Alignment.Center
			) {
				when (currentTab) {

					DevTab.Network -> NetworkPage()
					DevTab.Counter -> DevCounterTabPage()
					DevTab.NewLogin -> LoginControllerPage()
					DevTab.NewAuth -> AuthenticationControllerPage()

					DevTab.Entry -> DevExerciseEntryTabPage()
					DevTab.Type -> DevExerciseTypeTabPage()

					else -> Text("Disabled")
				}
			}
		}
	}
}
