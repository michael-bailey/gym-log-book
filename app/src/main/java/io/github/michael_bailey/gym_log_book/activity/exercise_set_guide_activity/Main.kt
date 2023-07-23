package io.github.michael_bailey.gym_log_book.activity.exercise_set_guide_activity

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import io.github.michael_bailey.gym_log_book.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Main(
	vm: SetGuideViewModelV2,
	onFinished: () -> Unit,
) {
	val items = listOf(
		ExerciseSetGuideActivityPage.Start,
		ExerciseSetGuideActivityPage.Set,
		ExerciseSetGuideActivityPage.Pause,
		ExerciseSetGuideActivityPage.AskExtraSet,
	)
	val nav = rememberNavController()

	// A surface container using the 'background' color from the theme
	Scaffold(
		topBar = {
			TopAppBar({ Text(text = stringResource(R.string.title_activity_exercise_set_guide)) })
		},
		content = {
			Surface(Modifier.padding(it)) {
				NavHost(
					navController = nav,
					startDestination = ExerciseSetGuideActivityPage.Start.route
				) {
					composable(ExerciseSetGuideActivityPage.Start.route) {
						StartPage(nav, vm)
					}
					composable(ExerciseSetGuideActivityPage.Set.route) {
						SetPage(nav, vm)
					}
					composable(ExerciseSetGuideActivityPage.Pause.route) {
						PausePage(nav, vm)
					}
					composable(ExerciseSetGuideActivityPage.AskExtraSet.route) {
						AskExtraSetPage(nav, onFinished)
					}
				}
			}
		}
	)

}