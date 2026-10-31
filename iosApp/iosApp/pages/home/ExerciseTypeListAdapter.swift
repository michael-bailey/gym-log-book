//
//  ExerciseTypeListAdapter.swift
//  iosApp
//
//  Created by michael bailey on 04/10/2026.
//

import Foundation

import iosComponents
import gym_client_kt

class ExerciseTypeListAdapter: BaseObservableModel {
	
	private let viewModel: IExerciseTypeTabViewModel
	private weak var observable: ExerciseTypeListView.ViewModel?
	
	init(
		viewModel: IExerciseTypeTabViewModel,
		observable: ExerciseTypeListView.ViewModel
	) {
		print("[ExerciseTypeListAdapter]: init")
		self.viewModel = viewModel
		self.observable = observable
		
		super.init()
		
		self.observable?.delegate = self
		
		track(viewModel.typeList) { it in
			observable.exerciseTypeList = it.map { it in
				self.convertFromKotlin(it)
			}
		}
	}
	
	private func convertFromKotlin(
		_ kotlin: IExerciseTypeTabViewModelExerciseTypeViewData
	) -> ExerciseTypeViewData {
		.init(
			UUID(uuidString: kotlin.id.toHexDashString())!,
			withName: kotlin.name,
			andExerciseClass: "Free Weight"
		)
	}
}

extension ExerciseTypeListAdapter: ExerciseTypeListView.ViewModel.Delegate {
	
}
