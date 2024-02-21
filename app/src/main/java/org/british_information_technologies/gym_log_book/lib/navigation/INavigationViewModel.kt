package org.british_information_technologies.gym_log_book.lib.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.LiveData
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import io.github.michael_bailey.android_chat_kit.extension.any.log

interface INavigationViewModel {

	val initialRoute: String

	val navigationState: LiveData<String>

	suspend fun navigateTo(route: String)

	@Composable
	fun launchNavigationState(nav: NavHostController) {
		val page by navigationState.observeAsState(initial = initialRoute)
		val currentPage by nav.currentBackStackEntryAsState()

		LaunchedEffect(page) {
			log(page)
			log(currentPage?.destination?.route ?: "null")
			if (page != currentPage?.destination?.route) {
				nav.navigate(page)
			}
		}
	}
}
