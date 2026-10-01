//
//  AddExerciseEntryFormAdapter.swift
//  iosApp
//
//  Created by michael bailey on 01/10/2026.
//

import Foundation

import iosComponents
import gym_client_kt

@MainActor
class AddExerciseEntryFormAdapter:
    BaseObservableModel,
    AddExerciseEntryFormView.ViewModel.Delegate {

    let viewModel: IExerciseEntryTabViewModel
    weak var observable: AddExerciseEntryFormView.ViewModel?

    init(
        viewModel: IExerciseEntryTabViewModel,
        observable: AddExerciseEntryFormView.ViewModel
    ) {
        self.viewModel = viewModel
        self.observable = observable

        super.init()

        self.observable?.delegate = self

        track(self.viewModel.exerciseTypesMap) { [weak self] map in
            self?.observable?.selectables = map.map { (key: KotlinUuid, value: String) in
                Selectable(id: UUID.init(uuidString: key.toHexDashString())!, name: value)
            }
        }
    }

    func submit(
        exerciseType: UUID,
        setNumber: Int32,
        weight: Double,
        reps: Int32
    ) {
        self.viewModel.submitCreateEntryForm(
            exerciseType: KotlinUuid.companion.parse(uuidString: exerciseType.uuidString),
            entrySetNumber: setNumber,
            entryWeight: weight,
            entryReps: reps
        )
    }
}
