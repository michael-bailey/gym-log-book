package io.github.michael_bailey.gym_log_book.add_weight_activity

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.navigation.ui.AppBarConfiguration
import io.github.michael_bailey.gym_log_book.databinding.ActivityAddWeightBinding

class AddWeightActivity : AppCompatActivity() {

	private lateinit var appBarConfiguration: AppBarConfiguration
	private lateinit var binding: ActivityAddWeightBinding

	private lateinit var WeightTextField: EditText

	private lateinit var SubmitButton: Button

	override fun onCreate(savedInstanceState: Bundle?) {
		WindowCompat.setDecorFitsSystemWindows(window, false)
		super.onCreate(savedInstanceState)

		binding = ActivityAddWeightBinding.inflate(layoutInflater)
		setContentView(binding.root)

		WeightTextField = binding.WeightTextEdit
		SubmitButton = binding.submitButton

		val viewModel: AddWeightViewModel by viewModels()

		WeightTextField.addTextChangedListener(viewModel.WeightWatcher)

		SubmitButton.setOnClickListener { submitData(viewModel) }
	}

	fun submitData(vm: AddWeightViewModel) {
		vm.finalise()
		finish()
	}

	fun onCancel(view: View) {
		finish()
	}
}