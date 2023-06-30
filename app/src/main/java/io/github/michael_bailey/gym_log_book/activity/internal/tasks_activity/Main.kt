package io.github.michael_bailey.gym_log_book.activity.internal.tasks_activity

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.michael_bailey.gym_log_book.lib.componenets.CardWithSwipeActions

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Main(vm: TaskActivityViewModel) {

	val tasks = vm.tasks.observeAsState(listOf())

	Scaffold(
		topBar = {
			TopAppBar(
				title = { Text("Tasks") },
			)
		},
		content = {
			Column(
				Modifier
					.fillMaxSize()
					.padding(it),
				Arrangement.Top,
				Alignment.CenterHorizontally
			) {
				LazyColumn(
					Modifier.fillMaxSize(0.91f),
					verticalArrangement = Arrangement.spacedBy(8.dp),
				) {
					items(tasks.value) { i ->
						CardWithSwipeActions(actions = {
							Button(onClick = { vm.deleteTask(i.id) }) {
								Text("Delete")
							}
						}) {
							Column(
								Modifier
									.fillMaxWidth()
									.wrapContentHeight()
									.padding(16.dp)
							) {
								Text(text = i.text)
							}
						}
					}
				}
			}
		},
		bottomBar = {
			NavigationBar {
				Row(
					Modifier
						.fillMaxSize()
						.padding(12.dp),
					Arrangement.SpaceBetween,
					Alignment.CenterVertically
				) {
					OutlinedTextField(
						value = vm.currentTaskString.value,
						onValueChange = vm::setTaskString,
						label = { Text("Task Text") })
					FloatingActionButton(
						containerColor = MaterialTheme.colorScheme.secondaryContainer,
						elevation = FloatingActionButtonDefaults.elevation(0.dp),
						onClick = vm::addTask
					) {
						Icon(Icons.Default.Add, "")
					}
				}
			}
		}
	)
}