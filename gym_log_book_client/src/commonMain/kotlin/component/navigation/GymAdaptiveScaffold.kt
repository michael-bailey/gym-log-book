package net.michael_bailey.gym_log_book.client.component.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffoldDefaults
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteType
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun <T : Any> GymAdaptiveScaffold(
	currentRoute: T,
	onNavigate: (T) -> Unit,
	modifier: Modifier = Modifier,
	fab: FabDefinition? = null,
	topBar: @Composable () -> Unit = {},
	navContent: GymNavScope<T>.() -> Unit,
	content: @Composable (PaddingValues) -> Unit,
) {
	// Rebuild each composition so nav items reflect latest state.
	val navScope = GymNavScope<T>().apply(navContent)

	// Mirrors the logic NavigationSuiteScaffold uses internally to pick a nav style
	val windowInfo = currentWindowAdaptiveInfo()
	val navType = NavigationSuiteScaffoldDefaults.calculateFromAdaptiveInfo(windowInfo)

	// The rail sits outside the Scaffold entirely so it spans the full screen height,
	// not just the content area below the TopAppBar
	Row(modifier = modifier.fillMaxSize()) {

		if (navType == NavigationSuiteType.NavigationRail) {
			GymNavigationRail(
				currentRoute = currentRoute,
				onNavigate = onNavigate,
				fab = fab,
				scope = navScope,
			)
		}

		Scaffold(
			modifier = Modifier.weight(1f),
			topBar = topBar,
			floatingActionButton = {
				if (navType == NavigationSuiteType.NavigationBar && fab != null) {
					FloatingActionButton(onClick = fab.onClick) {
						Icon(fab.icon, contentDescription = null)
					}
				}
			},
			bottomBar = {
				if (navType == NavigationSuiteType.NavigationBar) {
					GymNavigationBar(
						currentRoute = currentRoute,
						onNavigate = onNavigate,
						scope = navScope,
					)
				}
			}
		) { paddingValues ->
			content(paddingValues)
		}
	}
}
