package org.british_information_technologies.gym_log_book.activity.main_activity.dialogue

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import org.british_information_technologies.gym_log_book.lib.componenets.forms.AmendExerciseEntryForm
import org.british_information_technologies.gym_log_book.lib.interfaces.view_model.IExerciseEntryViewModel
import java.util.UUID

@Composable
fun ExerciseEntryModifyDialogue(
	vm: IExerciseEntryViewModel,
	id: UUID,
	dismiss: () -> Unit
) {
	val entries by vm.exerciseEntryList.observeAsState()
	val entry = entries?.find { it.id == id }
	val types by vm.exerciseNameMap.observeAsState(mapOf())

	Dialog(onDismissRequest = dismiss) {
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
					if (entry != null) {
						AmendExerciseEntryForm(
							exercise = entry,
							exercises = types,
							onSubmit = {
								vm.modifyExerciseEntry(it)
								dismiss()
							},
							onCancel = dismiss,
						)
					} else {
						CircularProgressIndicator()
					}
				}
			}
		)
	}
}