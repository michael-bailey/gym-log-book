package org.british_information_technologies.gym_log_book.activity.exercise_set_guide_activity

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import org.british_information_technologies.gym_log_book.R
import org.british_information_technologies.gym_log_book.activity.exercise_set_guide_activity.page.AskExtraSetPage
import org.british_information_technologies.gym_log_book.activity.exercise_set_guide_activity.page.PausePage
import org.british_information_technologies.gym_log_book.activity.exercise_set_guide_activity.page.SetPage
import org.british_information_technologies.gym_log_book.activity.exercise_set_guide_activity.page.StartPage
import org.british_information_technologies.gym_log_book.extensions.activity

@SuppressLint("UnusedContentLambdaTargetStateParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Main(
) {

	val vm = activity<ExerciseSetGuideActivity>().vm
	val pageState by vm.pageState.observeAsState(PageState.Start)

	// A surface container using the 'background' color from the theme
	Scaffold(
		topBar = {
			TopAppBar({ Text(text = stringResource(R.string.title_activity_exercise_set_guide)) })
		},
		content = {
			Surface(Modifier.padding(it)) {
				AnimatedContent(
					targetState = pageState, label = "",
				) { page ->
					when (page) {
						PageState.Start -> StartPage(vm = vm)
						PageState.SetEntry -> SetPage(vm = vm)
						PageState.Pause -> PausePage(vm = vm)
						PageState.ExtraSet -> AskExtraSetPage(vm = vm)
					}
				}
			}
		}
	)
}