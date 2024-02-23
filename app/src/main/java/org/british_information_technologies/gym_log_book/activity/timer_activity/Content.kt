package org.british_information_technologies.gym_log_book.activity.timer_activity

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContent
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun Content() {
	Column(
		modifier = Modifier.padding(
			bottom = WindowInsets.safeContent.asPaddingValues()
				.calculateBottomPadding()
		)
	) {
		LazyVerticalGrid(
			modifier = Modifier.weight(1f),
			columns = GridCells.Fixed(3),
			contentPadding = PaddingValues(
				start = 18.dp,
				end = 18.dp
			),
			content = {
				items(
					items = (1..99).toList(),
				) {
					Card(
						modifier = Modifier.padding(8.dp)
					) {
						Text(
							modifier = Modifier.padding(horizontal = 10.dp, vertical = 20.dp),
							text = "item: $it"
						)
					}
				}
			}
		)
		OutlinedTextField(value = "Hi there", onValueChange = {})
	}
}