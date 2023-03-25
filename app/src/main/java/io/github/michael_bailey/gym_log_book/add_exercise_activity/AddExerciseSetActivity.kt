package io.github.michael_bailey.gym_log_book.add_exercise_activity

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import io.github.michael_bailey.gym_log_book.R
import io.github.michael_bailey.gym_log_book.databinding.ActivityAddExerciseSetBinding
import kotlinx.coroutines.InternalCoroutinesApi

class AddExerciseSetActivity : AppCompatActivity() {

	private lateinit var binding: ActivityAddExerciseSetBinding

	private lateinit var ExerciseTextField: EditText
	private lateinit var SetNumberField: EditText
	private lateinit var WeightNumberField: EditText
	private lateinit var RepsTextField: EditText

	private lateinit var SubmitButton: Button
	private lateinit var CancelButton: Button

	@OptIn(InternalCoroutinesApi::class)
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		binding = ActivityAddExerciseSetBinding.inflate(layoutInflater)
		setContentView(binding.root)

		ExerciseTextField = binding.root.findViewById(R.id.ExerciseTextField)
		SetNumberField = binding.root.findViewById(R.id.SetNumberField)
		WeightNumberField = binding.root.findViewById(R.id.WeightNumberField)
		RepsTextField = binding.root.findViewById(R.id.RepsNumberField)

		SubmitButton = binding.root.findViewById(R.id.SubmitButton)
		CancelButton = binding.root.findViewById(R.id.CancelButton)

		// setup view model
		val viewModel: AddExerciseSetViewModel by viewModels()

		SetNumberField.addTextChangedListener(viewModel.SetNumberWatcher)
		WeightNumberField.addTextChangedListener(viewModel.WeightWatcher)
		ExerciseTextField.addTextChangedListener(viewModel.ExerciseWatcher)
		RepsTextField.addTextChangedListener(viewModel.RepsWatcher)

		CancelButton.setOnClickListener { finish() }
		SubmitButton.setOnClickListener { submitData(viewModel) }
	}

	private fun submitData(vm: AddExerciseSetViewModel) {
		vm.finalise()
		finish()
	}
}