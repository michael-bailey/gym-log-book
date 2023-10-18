package org.british_information_technologies.gym_log_book.activity.settings_activity.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarDropdownSelector(
	calendars: Map<Long, String>,
	selected: Long?,
	setCalendar: (Long) -> Unit
) {
	var dropdownExposed by remember { mutableStateOf(false) }
	val error = if (selected == null) {
		"Choose a calender"
	} else {
		""
	}

	Column {
		ExposedDropdownMenuBox(
			expanded = dropdownExposed,
			onExpandedChange = { dropdownExposed = !dropdownExposed }
		) {
			OutlinedTextField(
				modifier = Modifier.menuAnchor(),
				value = selected.toString(),
				label = { Text("Exercise...") },
				onValueChange = {},
				isError = (selected == null)
			)
			ExposedDropdownMenu(
				expanded = dropdownExposed,
				onDismissRequest = { dropdownExposed = false }) {
				for (i in calendars) {
					DropdownMenuItem(
						text = { Text(i.value) },
						onClick = { setCalendar(i.key) })
				}
			}
		}
		Text(error, color = Color.Red)
	}
}