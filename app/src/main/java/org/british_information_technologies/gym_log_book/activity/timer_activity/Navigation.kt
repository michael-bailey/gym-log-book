package org.british_information_technologies.gym_log_book.activity.timer_activity

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.british_information_technologies.gym_log_book.extension.activity
import org.british_information_technologies.gym_log_book.lib.form.Form

@Composable
fun Navigation() {

	val activity = activity<TimerActivity>()
	val vm = activity.vm
	val nav = rememberNavController()

	vm.LaunchNavigationState(nav)

	NavHost(navController = nav, startDestination = "home") {
		composable("home") {
			Box(
				modifier = Modifier.fillMaxSize(),
				contentAlignment = Alignment.Center
			) {
				Button(onClick = { vm.goNext() }) {
					Text("Next...")
				}
			}
		}

		composable("next") {
			Box(
				modifier = Modifier.fillMaxSize(),
				contentAlignment = Alignment.Center
			) {
				Form(
					onSubmit = { },
					onCancel = { }
				) {
					password("password")
				}
			}
		}
	}
}