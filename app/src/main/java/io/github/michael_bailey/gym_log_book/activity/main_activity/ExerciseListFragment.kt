package io.github.michael_bailey.gym_log_book.activity.main_activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.github.michael_bailey.gym_log_book.R
import io.github.michael_bailey.gym_log_book.app.App
import io.github.michael_bailey.gym_log_book.databinding.ExerciseFragmentBinding


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class ExerciseListFragment : Fragment() {
	companion object {
		const val TAG = "ExerciseListFragment"
	}

	// only valid between onCreateView and onDestroyView.
	private val binding get() = _binding!!
	private var _binding: ExerciseFragmentBinding? = null

	lateinit var recycler: RecyclerView

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		_binding = ExerciseFragmentBinding.inflate(inflater, container, false)

		recycler = binding.root.findViewById<RecyclerView>(R.id.ExerciseRecycler)
		recycler.layoutManager = LinearLayoutManager(context)

		val application = activity?.application as App

		recycler.adapter = application.exerciseTable.adapter

		return binding.root
	}

	override fun onDestroyView() {
		super.onDestroyView()
		_binding = null
	}
}