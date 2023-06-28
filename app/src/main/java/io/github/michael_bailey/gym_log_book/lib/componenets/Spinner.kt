import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Text
import androidx.compose.material3.DropdownMenu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.michael_bailey.gym_log_book.lib.Validator
import io.github.michael_bailey.gym_log_book.lib.componenets.ValidatorTextField
import io.github.michael_bailey.gym_log_book.theme.Gym_Log_BookTheme

@Composable
fun Spinner(
	modifier: Modifier? = null,
	list: List<String>,
	onSelect: (String) -> Unit,
) {

	var text by remember { mutableStateOf("") } // initial value
	val isOpen by remember { mutableStateOf(false) } // initial value

	val (state, setState) = remember { mutableStateOf(false) }


	Box {
		Column(modifier ?: Modifier) {
			ValidatorTextField(
				state = text,
				validator = Validator.StringNameValidator,
				placeholder = "Exercise...",
				onChange = { text = it }
			)
//			OutlinedTextField(
//				value = text.value,
//				onValueChange = { text.value = it },
//				label = { Text(text = "TextFieldTitle") },
//				modifier = Modifier.fillMaxWidth()
//			)
			DropdownMenu(
				modifier = Modifier.fillMaxWidth(),
				expanded = state,
				onDismissRequest = { setState(false) },
			) {
				list.forEach {
					DropdownMenuItem(
						modifier = Modifier.fillMaxWidth(),
						onClick = {
							setState(false)
							text = it
							onSelect(it)
						}
					) {
						Text(it, modifier = Modifier.wrapContentWidth())
					}
				}
			}
		}
		Spacer(
			modifier = Modifier
				.matchParentSize()
				.background(Color.Transparent)
				.padding(10.dp)
				.clickable(
					onClick = { setState(true) }
				)
		)
	}
}

@Composable
@Preview
fun SpinnerPreview() {

	val list = listOf(
		"option1",
		"option2",
		"option3",
		"option4",
		"option5",
		"option6",
		"option7"
	)

	val (text, setText) = remember { mutableStateOf("") }

	Gym_Log_BookTheme {
		Column(Modifier.fillMaxSize()) {
			Spinner(
				list = list,
				onSelect = setText
			)
		}

	}
}