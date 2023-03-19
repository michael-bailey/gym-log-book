package io.github.michael_bailey.gym_log_book.main_activity

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import io.github.michael_bailey.gym_log_book.R
import io.github.michael_bailey.gym_log_book.add_exercise_activity.AddExerciseSetActivity
import io.github.michael_bailey.gym_log_book.add_weight_activity.AddWeightActivity
import io.github.michael_bailey.gym_log_book.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

	private lateinit var appBarConfiguration: AppBarConfiguration
	private lateinit var binding: ActivityMainBinding

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		binding = ActivityMainBinding.inflate(layoutInflater)
		setContentView(binding.root)

		setSupportActionBar(binding.toolbar)

		val navController = findNavController(R.id.nav_host_fragment_content_main)
		appBarConfiguration = AppBarConfiguration(navController.graph)
		setupActionBarWithNavController(navController, appBarConfiguration)

		binding.fab.setOnClickListener { view ->
			var intent =
				Intent(applicationContext, AddExerciseSetActivity::class.java)
			startActivity(intent)
		}

		binding.mainNavBar.setOnItemSelectedListener {
			when (it.itemId) {
				R.id.action_exercise -> {
					navController.navigate(R.id.action_global_exercise_frag_nav)
					binding.fab.setOnClickListener {
						startActivity(
							Intent(
								applicationContext,
								AddExerciseSetActivity::class.java
							)
						)
					}
				}
				R.id.action_weight -> {
					navController.navigate(R.id.action_global_weight_frag_nav)
					binding.fab.setOnClickListener {
						startActivity(
							Intent(
								applicationContext,
								AddWeightActivity::class.java
							)
						)
					}
				}
				else -> {}
			}
			binding.toolbar.navigationIcon = null
			return@setOnItemSelectedListener true
		}
	}

	override fun onOptionsItemSelected(item: MenuItem): Boolean {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		return when (item.itemId) {
			R.id.action_exercise -> true
			else -> super.onOptionsItemSelected(item)
		}
	}

	override fun onSupportNavigateUp(): Boolean {
		val navController = findNavController(R.id.nav_host_fragment_content_main)
		return navController.navigateUp(appBarConfiguration)
			|| super.onSupportNavigateUp()
	}
}