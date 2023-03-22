package io.github.michael_bailey.gym_log_book.exercise_set_guide_activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import io.github.michael_bailey.gym_log_book.exercise_set_guide_activity.ui.theme.Gym_Log_BookTheme

class ExerciseSetGuideActivity : ComponentActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		setContent {
			val nav = rememberNavController()
			Gym_Log_BookTheme {
				// A surface container using the 'background' color from the theme
				Surface(
					modifier = Modifier.fillMaxSize(),
					color = MaterialTheme.colorScheme.background
				) {
					NavHost(navController = nav, startDestination = "start_page") {
						composable("start_page") {

							Button(onClick = {
								this@ExerciseSetGuideActivity.startPageButtonClick(
									nav
								)
							}) {
								Text("Click me")
							}
						}
						composable("set_page") {

							Button(onClick = {
								this@ExerciseSetGuideActivity.setPageButtonClick(
									nav
								)
							}) {
								Text("now me")
							}
						}
						composable("pause_page") {
							Button(onClick = {
								this@ExerciseSetGuideActivity.pausePageButtonClick(
									nav
								)
							}) {
								Text("finally me")
							}
						}
					}
					Greeting("Android")
				}
			}
		}
	}

	private fun startPageButtonClick(nav: NavHostController) {
		nav.navigate("set_page")
	}

	private fun setPageButtonClick(nav: NavHostController) {
		nav.navigate("pause_page")
	}

	private fun pausePageButtonClick(nav: NavHostController) {
		finish()
	}
}

@Composable
fun Greeting(name: String) {
	Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
	Gym_Log_BookTheme {
		Greeting("Android")
	}
}