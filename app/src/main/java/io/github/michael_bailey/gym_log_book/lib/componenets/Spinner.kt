import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material3.DropdownMenu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun Spinner(
	requestToOpen: Boolean = false,
	list: List<String>,
	request: (Boolean) -> Unit,
	selectedString: (String) -> Unit
) {
	DropdownMenu(
		modifier = Modifier.fillMaxWidth(),
		expanded = requestToOpen,
		onDismissRequest = { request(false) },
	) {
		list.forEach {
			DropdownMenuItem(
				modifier = Modifier.fillMaxWidth(),
				onClick = {
					request(false)
					selectedString(it)
				}
			) {
				Text(it, modifier = Modifier.wrapContentWidth())
			}
		}
	}
}

@Composable
fun CountrySelection() {
	val countryList = listOf(
		"United state",
		"Australia",
		"Japan",
		"India",
	)
	val text = remember { mutableStateOf("") } // initial value
	val isOpen = remember { mutableStateOf(false) } // initial value
	val openCloseOfDropDownList: (Boolean) -> Unit = {
		isOpen.value = it
	}
	val userSelectedString: (String) -> Unit = {
		text.value = it
	}
	Box {
		Column {
			OutlinedTextField(
				value = text.value,
				onValueChange = { text.value = it },
				label = { Text(text = "TextFieldTitle") },
				modifier = Modifier.fillMaxWidth()
			)
			Spinner(
				requestToOpen = isOpen.value,
				list = countryList,
				openCloseOfDropDownList,
				userSelectedString
			)
		}
		Spacer(
			modifier = Modifier
          .matchParentSize()
          .background(Color.Transparent)
          .padding(10.dp)
          .clickable(
              onClick = { isOpen.value = true }
          )
		)
	}
}