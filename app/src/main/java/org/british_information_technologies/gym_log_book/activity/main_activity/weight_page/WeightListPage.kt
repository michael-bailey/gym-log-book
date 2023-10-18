package org.british_information_technologies.gym_log_book.activity.main_activity.weight_page

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.british_information_technologies.gym_log_book.activity.main_activity.MainActivityViewModel
import org.british_information_technologies.gym_log_book.theme.Title

@Composable
fun WeightListPage(vm: MainActivityViewModel) {
	val weightList by vm.weightEntryList.observeAsState(initial = listOf())

	val arrangement = remember {
		derivedStateOf {
			if (weightList.isNotEmpty()) {
				Arrangement.Top
			} else {
				Arrangement.Center
			}
		}
	}

	Column(
		modifier = Modifier.fillMaxSize(),
		verticalArrangement = arrangement.value,
		horizontalAlignment = Alignment.CenterHorizontally
	) {

		LazyColumn(
			Modifier,
			contentPadding = PaddingValues(top = 8.dp, bottom = 8.dp),
			verticalArrangement = Arrangement.spacedBy(8.dp),
			horizontalAlignment = Alignment.CenterHorizontally,
			state = vm.weightListState
		) {
			if (weightList.isEmpty()) {
				item {
					Column(
						Modifier
							.fillMaxWidth()
							.height(250.dp),
						verticalArrangement = Arrangement.Center,
						horizontalAlignment = Alignment.CenterHorizontally
					) {
						Text(text = "You Haven't added any Weights")
						Text(text = "Click the 'add Weight' button to add one")
					}
				}
			} else {

				item {
					Column(
						Modifier
							.fillMaxWidth()
							.height(250.dp),
						verticalArrangement = Arrangement.Center,
						horizontalAlignment = Alignment.CenterHorizontally
					) {
						Text("Weights", fontSize = Title)
					}
				}

				items(weightList) { item ->
					WeightItemView(
						modifier = Modifier.fillMaxWidth(0.91f),
						item = item,
						onModify = { /* TODO */ },
						onDelete = { vm.deleteWeightEntry(it) },
					)
				}
			}
		}
	}
}