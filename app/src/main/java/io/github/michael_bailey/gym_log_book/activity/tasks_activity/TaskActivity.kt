package io.github.michael_bailey.gym_log_book.activity.tasks_activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.room.Room
import io.github.michael_bailey.gym_log_book.database.InternalDatabase
import io.github.michael_bailey.gym_log_book.theme.Gym_Log_BookTheme

class TaskActivity : ComponentActivity() {

	lateinit var db: InternalDatabase

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		db = Room.databaseBuilder(
			applicationContext,
			InternalDatabase::class.java,
			"gym_log_book_debug_db"
		).allowMainThreadQueries().build()

		val vm: TaskActivityViewModel by viewModels {
			TaskActivityViewModelFactory(db.taskDao())
		}

		setContent {
			Gym_Log_BookTheme(colourNavBar = true) {
				// A surface container using the 'background' color from the theme
				Surface(
					modifier = Modifier.fillMaxSize(),
					color = MaterialTheme.colorScheme.background
				) {
					Main(vm)
				}
			}
		}
	}
}

