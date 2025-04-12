package org.british_information_technologies.gym_log_book.activity.settings_activity

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun Main(vm: SettingsViewModel) {

//	val calendarMap = vm.calendarMap ?: mapOf()
//	val selectedCalendar by vm.selectedCalenderEntry.observeAsState(initial = null)

	Scaffold(
		content = {
			LazyColumn(modifier = Modifier.padding(it), content = {
				item {
					Text(text = "Hello world")
//					CalendarDropdownSelector(
//						calendarMap,
//						selectedCalendar,
//						{ vm.setCalendar(it) }
//					)
				}
			})
		}
	)
}