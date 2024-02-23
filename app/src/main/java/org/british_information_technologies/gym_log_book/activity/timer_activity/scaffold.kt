package org.british_information_technologies.gym_log_book.activity.timer_activity

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PageScaffold(
	modifier: Modifier = Modifier,
	titleText: String = "Replace this",
	content: @Composable () -> Unit,
) {
	Scaffold(
		modifier = modifier,
		topBar = { TopAppBar(title = { Text(text = titleText) }) },
		contentWindowInsets = WindowInsets(0.dp, 0.dp, 0.dp, 0.dp),
		content = {
			Surface(
				modifier = Modifier
					.fillMaxSize()
					.padding(it)

					.padding(horizontal = 18.dp),
				color = MaterialTheme.colorScheme.surfaceVariant,
				shape = RoundedCornerShape(
					topStart = 20.dp,
					topEnd = 20.dp,
					bottomStart = 0.dp,
					bottomEnd = 0.dp
				),

				content = {
					Box(
						modifier = Modifier
							.background(Color.Red),
						contentAlignment = Alignment.Center,
						content = {
							Navigation()
						}
					)
				}
			)
		}
	)
}