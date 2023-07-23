package io.github.michael_bailey.gym_log_book.lib.componenets.dialogues

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import io.github.michael_bailey.gym_log_book.lib.interfaces.view_model.IExerciseTypeViewModel
import java.util.UUID

@Composable
fun ExerciseTypeModifyDialogue(
	vm: IExerciseTypeViewModel,
	id: UUID,
	onDismiss: () -> Unit
) {
	Dialog(onDismissRequest = onDismiss) {
		Surface(
			modifier = Modifier
				.fillMaxWidth()
				.wrapContentHeight(),
			shape = RoundedCornerShape(28.dp),
			content = {
				Column(
					Modifier
						.fillMaxWidth()
						.padding(24.dp),
				) {
					Text("Modifying Exercise Type")

				}
			}
		)
	}
}