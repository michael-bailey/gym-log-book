package io.github.michael_bailey.gym_log_book.activity.internal.debug_settings_activity

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import io.github.michael_bailey.gym_log_book.activity.internal.debug_settings_activity.setting_widget.SettingsGatekeeperWidget
import io.github.michael_bailey.gym_log_book.activity.internal.debug_settings_activity.setting_widget.SettingsToggleWidget
import io.github.michael_bailey.gym_log_book.theme.StickyHeader

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun Main(vm: DebugSettingsViewModel) {

	val isDebugEnabled = vm.isDebugEnabled.observeAsState(false)
	val isBottomNavEnabled = vm.isDebugBottomNavBarEnabled.observeAsState(false)
	val isStatusColourEnabled =
		vm.isDebugStatusBarColourEnabled.observeAsState(false)
	val isNavbarColourEnabled =
		vm.isDebugNavBarColourEnabled.observeAsState(false)

	val gatekeepers = vm.gatekeeperList.map {
		it.first
	}

	Scaffold(
		topBar = {
			TopAppBar(title = { Text("Settings") }
			)
		},
		content = {
			Surface(
				modifier = Modifier.padding(it)
			) {
				Column(
					Modifier.fillMaxSize(),
					Arrangement.Center,
					Alignment.CenterHorizontally
				) {

					Column(
						Modifier
							.fillMaxWidth(0.9f)
							.wrapContentHeight(),
						verticalArrangement = Arrangement.Top,
						horizontalAlignment = Alignment.Start
					) {
						Card(
							Modifier
								.fillMaxWidth()
								.wrapContentHeight(),
							shape = RoundedCornerShape(28.dp),
							colors = CardDefaults.cardColors(
								MaterialTheme.colorScheme.primaryContainer
							),
						) {
							SettingsToggleWidget(
								Modifier
									.fillMaxWidth()
									.padding(24.dp, 16.dp),
								name = "Debug",
								state = isDebugEnabled.value,
								onChange = vm::setIsDebugEnabled
							)
						}
					}

					Spacer(modifier = Modifier.height(32.dp))

					LazyColumn(
						Modifier
							.fillMaxSize(0.9f),
						verticalArrangement = Arrangement.Top,
						horizontalAlignment = Alignment.Start
					) {
						spacedStickyHeader("Debug UI Toggles")

						item {
							SettingsToggleWidget(
								Modifier.fillMaxWidth(),
								name = "Bottom Nav",
								state = isBottomNavEnabled.value,
								onChange = vm::setIsBottomNavEnabled,
								enabled = isDebugEnabled.value
							)
						}

						item {
							SettingsToggleWidget(
								Modifier.fillMaxWidth(),
								name = "Status Colour",
								state = isStatusColourEnabled.value,
								onChange = vm::setIsStatusColourEnabled,
								enabled = isDebugEnabled.value
							)
						}

						item {
							SettingsToggleWidget(
								Modifier.fillMaxWidth(),
								name = "Navbar Colour",
								state = isNavbarColourEnabled.value,
								onChange = vm::setIsNavbarColourEnabled,
								enabled = isDebugEnabled.value
							)
						}

						spacer()

						spacedStickyHeader("Beta Features")

						items(gatekeepers) { name ->
							SettingsGatekeeperWidget(vm = vm, gatekeeperName = name)
						}

						spacer()

						spacedStickyHeader("Data Actions")

						item {
							Button(onClick = { vm.runDataMigration() }) {
								Text("Migrate data")
							}
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

fun LazyListScope.spacer() {
	item {
		Spacer(modifier = Modifier.height(26.dp))
	}
}

@OptIn(ExperimentalFoundationApi::class)
fun LazyListScope.spacedStickyHeader(text: String) {
	stickyHeader {
		Column {
			Text(text, fontSize = StickyHeader, fontWeight = FontWeight(550))
			Spacer(modifier = Modifier.height(8.dp))
		}
	}
}