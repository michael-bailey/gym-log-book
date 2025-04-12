/* While this template provides a good starting point for using Wear Compose, you can always
 * take a look at https://github.com/android/wear-os-samples/tree/main/ComposeStarter to find the
 * most up to date changes to the libraries and their usages.
 */

package org.british_information_technologies.gym_watch_app.activity.exercise_list

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ExerciseListActivity : ComponentActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		installSplashScreen()

		super.onCreate(savedInstanceState)

		setTheme(android.R.style.Theme_DeviceDefault)

		setContent {
			Main("Android")
		}
	}
}
