package org.british_information_technologies.gym_log_book.activity.data_export

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import org.british_information_technologies.gym_log_book.extension.activity

@Composable
fun Main() {

	val activity = activity<DataExportActivity>()
	val status by activity.vm.status.observeAsState(initial = WriteStatus.Unknown)
	val itemCount by activity.vm.itemCount.observeAsState(initial = 0)

	AlertDialog(
		onDismissRequest = { activity.finish() },
		text = {
			Text(
				text = when (status) {
					WriteStatus.NotStarted -> """
						Clicking "Start" will start the export.
						The data is saved as JSON.
						
						Select a file on the next page.
						
						When you are ready press "Start".
					""".trimIndent()

					WriteStatus.RequestingFile -> "Requesting File..."
					WriteStatus.FailedToRequest -> "failed to request file, press start to try again"
					WriteStatus.GotURL -> "Attempting Write..."
					WriteStatus.Success -> "Written Data Successfully"
					WriteStatus.FailedToFind -> "Couldn't open that, please try again with a different location"
					WriteStatus.FailedToWrite -> "Couldn't Write there, please try again with a different location"
					WriteStatus.Unknown -> "Please wait, something isn't setup"
				}
			)
		},
		confirmButton = {
			Button(
				enabled = when (status) {
					WriteStatus.NotStarted, WriteStatus.FailedToRequest, WriteStatus.FailedToFind, WriteStatus.FailedToWrite -> true
					else -> false
				},
				onClick = {
					activity.createJsonFile()
				}
			) {
				Text(text = "Start")
			}
		},
		dismissButton = {
			Button(
				enabled = when (status) {
					WriteStatus.NotStarted, WriteStatus.FailedToRequest, WriteStatus.FailedToFind, WriteStatus.FailedToWrite -> true
					else -> false
				},
				onClick = {
					activity.finish()
				}
			) {
				Text(text = "Cancel")
			}
		}
	)
}