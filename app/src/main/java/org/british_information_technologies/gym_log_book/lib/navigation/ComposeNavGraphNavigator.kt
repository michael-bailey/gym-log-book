package org.british_information_technologies.gym_log_book.lib.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraph
import androidx.navigation.NavGraphNavigator
import androidx.navigation.Navigator
import androidx.navigation.NavigatorProvider

@Navigator.Name("navigation")
internal class ComposeNavGraphNavigator(
	navigatorProvider: NavigatorProvider
) : NavGraphNavigator(navigatorProvider) {
	override fun createDestination(): NavGraph {
		return ComposeNavGraph(this)
	}

	internal class ComposeNavGraph(
		navGraphNavigator: Navigator<out NavGraph>
	) : NavGraph(navGraphNavigator) {
		internal var enterTransition: (@JvmSuppressWildcards
		AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition?)? =
			null

		internal var exitTransition: (@JvmSuppressWildcards
		AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition?)? =
			null

		internal var popEnterTransition: (@JvmSuppressWildcards
		AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition?)? =
			null

		internal var popExitTransition: (@JvmSuppressWildcards
		AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition?)? =
			null
	}
}