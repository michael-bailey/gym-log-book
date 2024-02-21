package org.british_information_technologies.gym_log_book.activity.exercise_set_guide_activity

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.british_information_technologies.gym_log_book.R
import org.british_information_technologies.gym_log_book.activity.exercise_set_guide_activity.page.ExerciseSetGuideActivityPage
import org.british_information_technologies.gym_log_book.extension.activity

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Main(
) {

	val items = listOf(
		ExerciseSetGuideActivityPage.Start,
		ExerciseSetGuideActivityPage.Set,
		ExerciseSetGuideActivityPage.Pause,
		ExerciseSetGuideActivityPage.AskExtraSet,
	)

	val vm = activity<ExerciseSetGuideActivity>().vm
	val nav = rememberNavController()

	vm.launchNavigationState(nav)

	// A surface container using the 'background' color from the theme
	Scaffold(
		topBar = {
			TopAppBar({ Text(text = stringResource(R.string.title_activity_exercise_set_guide)) })
		},
		content = {
			Surface(Modifier.padding(it)) {
				NavHost(navController = nav, startDestination = vm.initialRoute) {
					for (i in items) {
						composable(i.route) { i.content(vm) }
					}
				}
			}
		}
	)
}