package org.british_information_technologies.gym_log_book.activity.data_export

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.british_information_technologies.gym_log_book.theme.Gym_Log_BookTheme
import kotlin.time.Duration.Companion.seconds

@AndroidEntryPoint
class DataExportActivity : ComponentActivity() {

	val vm by viewModels<DataExportActivityViewModel>()

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		lifecycle.addObserver(vm)

		// on success kill the activity
		vm.status.observe(this) {
			when (it) {
				WriteStatus.RequestingFile -> createJsonFile()
				WriteStatus.Success -> {
					lifecycleScope.launch {
						delay(3.seconds)
						finish()
					}
				}

				else -> {}
			}
		}

		setContent {
			Gym_Log_BookTheme {
				Main()
			}
		}
	}

	fun createJsonFile() {
		lifecycleScope.launch {
			val intent = Intent(Intent.ACTION_CREATE_DOCUMENT).apply {
				addCategory(Intent.CATEGORY_OPENABLE)
				type = "application/json"
				putExtra(Intent.EXTRA_TITLE, "Gym_log_book_data.json")
			}
			startActivityForResult(intent, CREATE_FILE)
		}
	}

	@Deprecated(
		"This is still usable. Ignore the deprecation what it says",
		level = DeprecationLevel.WARNING
	)
	override fun onActivityResult(
		requestCode: Int,
		resultCode: Int,
		data: Intent?
	) {
		super.onActivityResult(requestCode, resultCode, data)

		if (requestCode == CREATE_FILE) {
			vm.writeData(requestCode, resultCode, data, contentResolver)
		}
	}


	companion object {
		const val CREATE_FILE = 1
	}
}
