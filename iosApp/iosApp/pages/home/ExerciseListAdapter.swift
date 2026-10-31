//
//  ExerciseListAdapter.swift
//  iosApp
//
//  Created by michael bailey on 25/09/2026.
//

import Foundation
import SwiftUI
import Observation
import iosComponents

import gym_client_kt

class ExerciseListAdapter: BaseObservableModel, ExerciseListView.ViewModel.Delegate {

    private let viewModel: IExerciseEntryTabViewModel
    private weak var observable: ExerciseListView.ViewModel?

    init(viewModel: IExerciseEntryTabViewModel, observable: ExerciseListView.ViewModel) {
        self.viewModel = viewModel
        self.observable = observable

        super.init()

        self.observable?.delegate = self

        track(viewModel.allEntries) { it in
            observable.allEntries = it.map { it in
                self.convertFromKotlin(it)
            }
        }
    }

    private func convertFromKotlin(
        _ kotlin: Gym_log_book_sharedExerciseEntryView
    ) -> ExerciseEntryViewData {
        ExerciseEntryViewData(
            UUID.init(uuidString: kotlin.id.toHexDashString())!,
            withTypeName: kotlin.exerciseTypeName,
            andSetNumber: Int(kotlin.setNumber),
            andWeight: Float(kotlin.weight),
            andReps: Int(kotlin.reps)
        )
    }
}
