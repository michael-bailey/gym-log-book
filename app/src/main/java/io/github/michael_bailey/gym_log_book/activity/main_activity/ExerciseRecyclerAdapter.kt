package io.github.michael_bailey.gym_log_book.activity.main_activity

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import io.github.michael_bailey.gym_log_book.R
import io.github.michael_bailey.gym_log_book.data_type.ExerciseItem
import io.github.michael_bailey.gym_log_book.table.ExerciseTable

/// an adapter that will handle the display of a exercise
/// in a recycler view
class ExerciseRecyclerAdapter(
	private val table: ExerciseTable,
	private val onClick: (View) -> Unit
) : RecyclerView.Adapter<ExerciseRecyclerAdapter.ViewHolder>() {

	// Create new views (invoked by the layout manager)
	override fun onCreateViewHolder(
		viewGroup: ViewGroup,
		viewType: Int
	): ViewHolder {
		// Create a new view, which defines the UI of the list item
		val view = LayoutInflater.from(viewGroup.context)
			.inflate(R.layout.exercise_item, viewGroup, false)

		return ViewHolder(view, onClick)
	}

	// Replace the contents of a view (invoked by the layout manager)
	override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

		// Get element from your dataset at this position and replace the
		// contents of the view with that element
		viewHolder.setData(table.liveData.value!![position])
	}

	// Return the size of your dataset (invoked by the layout manager)
	override fun getItemCount() = table.getRowCount()

	class ViewHolder(view: View, val onClick: (View) -> Unit) :
		RecyclerView.ViewHolder(view) {
		val exerciseNameView: TextView
		val exerciseSetNumberView: TextView
		val exerciseWeigntView: TextView
		val exerciseRepsView: TextView

		init {
			exerciseNameView = view.findViewById(R.id.exercise_name)
			exerciseSetNumberView = view.findViewById(R.id.exercise_set_number)
			exerciseWeigntView = view.findViewById(R.id.exercise_weight)
			exerciseRepsView = view.findViewById(R.id.exercise_reps)

			view.setOnClickListener(onClick)
		}

		fun setData(item: ExerciseItem) {
			exerciseNameView.text = item.exercise
			exerciseSetNumberView.text = item.setNumber.toString()
			exerciseWeigntView.text = item.weight.toString()
			exerciseRepsView.text = item.reps.toString()
		}
	}
}