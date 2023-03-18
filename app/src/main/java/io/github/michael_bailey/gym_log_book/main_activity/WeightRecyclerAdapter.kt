package io.github.michael_bailey.gym_log_book.main_activity

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import io.github.michael_bailey.gym_log_book.R
import io.github.michael_bailey.gym_log_book.data_manager.WeightTable
import io.github.michael_bailey.gym_log_book.data_type.WeightItem

/// an adapter that will handle the display of a exercise
/// in a recycler view
class WeightRecyclerAdapter(
	private val table: WeightTable,
	private val onClick: (WeightItem) -> Unit
) : RecyclerView.Adapter<WeightRecyclerAdapter.ViewHolder>() {

	// Create new views (invoked by the layout manager)
	override fun onCreateViewHolder(
		viewGroup: ViewGroup,
		viewType: Int
	): ViewHolder {
		// Create a new view, which defines the UI of the list item
		val view = LayoutInflater.from(viewGroup.context)
			.inflate(R.layout.weight_item, viewGroup, false)

		return ViewHolder(view, onClick)
	}

	// Replace the contents of a view (invoked by the layout manager)
	override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

		// Get element from your dataset at this position and replace the
		// contents of the view with that element
		viewHolder.setData(table.store[position])
	}

	// Return the size of your dataset (invoked by the layout manager)
	override fun getItemCount() = table.store.size

	class ViewHolder(view: View, val onClick: (WeightItem) -> Unit) :
		RecyclerView.ViewHolder(view) {
		val exerciseWeigntView: TextView

		init {

			exerciseWeigntView = view.findViewById(R.id.weight_item_weight)

		}

		fun setData(item: WeightItem) {

			exerciseWeigntView.text = item.weight.toString()

		}
	}
}