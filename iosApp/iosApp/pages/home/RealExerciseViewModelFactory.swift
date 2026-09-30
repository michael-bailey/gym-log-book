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

final class RealExerciseViewModelFactory: ExerciseListViewModelFactory {

    let authenticatedComponent: AuthenticatedScope

    init(authenticatedComponent: AuthenticatedScope) {
        self.authenticatedComponent = authenticatedComponent
    }

    func create() -> iosComponents.ExerciseListView.ViewModel {
        let viewModel = authenticatedComponent.exerciseEntryListViewModel
        let observable = ExerciseListView.ViewModel()

        let _ = ExerciseListAdapter(viewModel: viewModel, observable: observable)

        return observable
    }

    class ExerciseListAdapter: ExerciseListView.ViewModel.Delegate {
        private let tracker = BaseObservableModel()
        private let observable: ExerciseListView.ViewModel
        private let viewModel: IExerciseEntryTabViewModel

        init(viewModel: IExerciseEntryTabViewModel, observable: ExerciseListView.ViewModel) {
            self.viewModel = viewModel
            self.observable = observable

            self.observable.delegate = self

            tracker.track(viewModel.allEntries) { it in
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
}
