package org.british_information_technologies.gym_log_book.activity.timer_activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Call
import androidx.compose.material.icons.outlined.Dialpad
import androidx.compose.material.icons.outlined.Send
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import dagger.hilt.android.AndroidEntryPoint
import org.british_information_technologies.gym_log_book.theme.Gym_Log_BookTheme

@AndroidEntryPoint
class TimerActivity : ComponentActivity() {

	val vm by viewModels<TimerActivityViewModel>()

	@OptIn(ExperimentalMaterial3Api::class)
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		setContent {
			Gym_Log_BookTheme {
				// A surface container using the 'background' color from the theme
				Row(
					modifier = Modifier.fillMaxSize()
				) {
					Row(
						modifier = Modifier.weight(1f),
					) {
						NavigationRail(
							header = {
								FloatingActionButton(
									onClick = { /*TODO*/ },
									content = {
										Icon(
											imageVector = Icons.Outlined.Add,
											contentDescription = ""
										)
									}
								)
							},
							content = {
								NavigationRailItem(
									selected = true,
									onClick = { /*TODO*/ },
									icon = {
										Icon(
											imageVector = Icons.Outlined.Dialpad,
											contentDescription = ""
										)
									},
									label = { Text(text = "Dial") }
								)
								NavigationRailItem(
									selected = false,
									onClick = { /*TODO*/ },
									icon = {
										Icon(
											imageVector = Icons.Outlined.Send,
											contentDescription = ""
										)
									},
									label = { Text(text = "Send") }
								)
								NavigationRailItem(
									selected = false,
									onClick = { /*TODO*/ },
									icon = {
										Icon(
											imageVector = Icons.Outlined.Call,
											contentDescription = ""
										)
									},
									label = { Text(text = "Call") }
								)
							}
						)
						Surface(
							modifier = Modifier
								.fillMaxSize()
								.weight(1f)
						) {
							Box(
								contentAlignment = Alignment.Center,
							) {
								Content()
							}
						}
					}

					PageScaffold(
						modifier = Modifier.weight(1f),
						titleText = "Content",
						content = {
							Navigation()
						}
					)
				}
			}
		}
	}
}