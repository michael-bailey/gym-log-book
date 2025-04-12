package org.british_information_technologies.gym_log_book.activity.main_activity

import android.app.Activity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import org.british_information_technologies.gym_log_book.activity.main_activity.dialogue.ExerciseEntryModifyDialogue
import org.british_information_technologies.gym_log_book.activity.main_activity.dialogue.ExerciseTypeCreateDialogue
import org.british_information_technologies.gym_log_book.activity.main_activity.dialogue.ExerciseTypeModifyDialogue
import org.british_information_technologies.gym_log_book.activity.main_activity.dialogue.WeightCreateDialogue
import org.british_information_technologies.gym_log_book.activity.main_activity.home_page.HomePage
import org.british_information_technologies.gym_log_book.activity.main_activity.home_page.exercise_types_tab.ExerciseTypeListPage
import org.british_information_technologies.gym_log_book.activity.main_activity.home_page.exercises_tab.ExerciseListPage
import org.british_information_technologies.gym_log_book.activity.main_activity.weight_page.WeightListPage
import org.british_information_technologies.gym_log_book.lib.navigation.NavLocal
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Main(vm: MainActivityViewModel) {
	val activity = LocalContext.current as Activity
	val nav = NavLocal.current

	val URI = "gym_log_book"


	val scrollState = TopAppBarDefaults.pinnedScrollBehavior()

	Surface(modifier = Modifier.fillMaxSize()) {
		NavHost(
			navController = nav!!,
			startDestination = "home",
		) {
			composable(route = "home") {
				HomePage()
			}
			composable(
				route = "exercise_page",
				deepLinks = listOf(
					navDeepLink { uriPattern = "$URI/exercises" }
				)
			) { ExerciseListPage(vm = vm) }

			composable(
				route = "exercise_type_page",
				deepLinks = listOf(
					navDeepLink { uriPattern = "$URI/exercise_types" }
				)

			) { ExerciseTypeListPage(vm = vm) }
			composable(
				route = "weight_page",
				deepLinks = listOf(
					navDeepLink { uriPattern = "$URI/weight" }
				)

			) { WeightListPage(vm = vm) }

			// dialogues
			dialog(route = "add_exercise_type_dialogue") {
				ExerciseTypeCreateDialogue(vm)
			}

			dialog(
				route = "modify_exercise_type_dialogue/{id}",
				arguments = listOf(
					navArgument(name = "id") {
						this.nullable = false
						this.type = NavType.StringType
					}
				),
			) {
				val stringID = it.arguments?.getString("id")
				if (stringID == null) {
					nav.popBackStack()
				}
				val id = UUID.fromString(stringID)
				ExerciseTypeModifyDialogue(vm = vm, id = id)
			}

			dialog(
				route = "modify_exercise_dialogue/{id}",
				arguments = listOf(
					navArgument(name = "id") {
						this.nullable = false
						this.type = NavType.StringType
					}
				),
			) {
				val stringID = it.arguments?.getString("id")
				if (stringID == null) {
					nav.popBackStack()
				}
				val id = UUID.fromString(stringID)
				ExerciseEntryModifyDialogue(vm = vm, id = id)
			}

			dialog(route = "add_weight_dialogue") {
				WeightCreateDialogue(vm = vm)
			}
		}
	}
}


//NavigationFab(
//label = stringResource(R.string.add_exercise_type_fab_string),
//icon = Icons.Outlined.Add,
//shownRoute = "exercise_type_page",
//route = "add_exercise_type_dialogue"
//)
//NavigationFab(
//label = stringResource(R.string.add_weight_fab_string),
//icon = Icons.Outlined.Add,
//shownRoute = "weight_page",
//route = "add_weight_dialogue"
//)