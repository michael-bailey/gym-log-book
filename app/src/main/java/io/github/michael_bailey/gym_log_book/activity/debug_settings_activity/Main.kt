package io.github.michael_bailey.gym_log_book.activity.debug_settings_activity

import android.app.Activity
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallTopAppBar
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import io.github.michael_bailey.gym_log_book.App
import io.github.michael_bailey.gym_log_book.activity.debug_settings_activity.setting_widget.SettingsToggleWidget

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun Main(vm: DebugSettingsViewModel) {

	val activity = LocalContext.current as Activity

	val isDebugEnabled = vm.isDebugEnabled.observeAsState(false)
	val isBottomNavEnabled = vm.isDebugBottomNavBarEnabled.observeAsState(false)
	val isStatusColourEnabled =
		vm.isDebugStatusBarColourEnabled.observeAsState(false)
	val isNavbarColourEnabled =
		vm.isDebugNavBarColourEnabled.observeAsState(false)

	Scaffold(
		topBar = {
			SmallTopAppBar(
				title = { Text("Settings") }
			)
		},
		content = {
			Surface(
				modifier = Modifier.padding(it)
			) {
				LazyColumn(
					Modifier
						.fillMaxSize(0.9f),
					verticalArrangement = Arrangement.Top,
					horizontalAlignment = Alignment.Start
				) {
					stickyHeader {
						Text("Debug Toggles")
					}

					item {
						SettingsToggleWidget(
							name = "Debug",
							state = isDebugEnabled.value,
							onChange = vm::setIsDebugEnabled
						)
					}

					item {
						SettingsToggleWidget(
							name = "Bottom Nav",
							state = isBottomNavEnabled.value,
							onChange = vm::setIsBottomNavEnabled,
							enabled = isDebugEnabled.value
						)
					}

					item {
						SettingsToggleWidget(
							name = "Status Colour",
							state = isStatusColourEnabled.value,
							onChange = vm::setIsStatusColourEnabled,
							enabled = isDebugEnabled.value
						)
					}

					item {
						SettingsToggleWidget(
							name = "Navbar Colour",
							state = isNavbarColourEnabled.value,
							onChange = vm::setIsNavbarColourEnabled,
							enabled = isDebugEnabled.value
						)
					}

					item {
						Button(onClick = {
							(activity.application as App)
								.appNotificationManager.postTimerNotification(
									activity,
									"Test",
									"10",
									"10"
								)
						}) {
							Text("Post Timer Notification")
						}
					}

					item {
						Button(onClick = { (activity.application as App).appNotificationManager.cancelTimerNotification() }) {
							Text("Cancel Timer Notification")
						}
					}
				}
			}
		},
		bottomBar = {
			if (isBottomNavEnabled.value) {
				NavigationBar {
					Text("This is to be iomplemented")
				}
			}
		}
	)


}