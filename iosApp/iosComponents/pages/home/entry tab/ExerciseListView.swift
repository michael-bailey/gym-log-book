//
//  ExerciseListView.swift
//  iosComponents
//
//  Created by michael bailey on 25/09/2026.
//

import SwiftUI


public struct ExerciseListView: View {

    @State var viewModel: ViewModel

    public var body: some View {
        List(viewModel.allEntries, id: \.id) { entry in
            ExerciseListEntryView(data: entry)
        }
        .navigationTitle("Exercise Entries")
        #if os(iOS)
        .navigationBarTitleDisplayMode(.large)
        #endif
    }

    public init(viewModel: ViewModel) {
        self.viewModel = viewModel
    }

    @Observable
    public class ViewModel {

        public var allEntries: [ExerciseEntryViewData]

        public var delegate: Delegate? = nil

        public init(startingEntries: [ExerciseEntryViewData] = []) {
            self.allEntries = startingEntries
        }

        public protocol Delegate {

        }
    }
}

public protocol ExerciseListViewModelFactory {
    func create() -> ExerciseListView.ViewModel
}

public extension EnvironmentValues {
    @Entry var exerciseListViewModelFactory: any ExerciseListViewModelFactory = PreviewExerciseListViewModelFactory()
}

struct PreviewExerciseListViewModelFactory: ExerciseListViewModelFactory {
    func create() -> ExerciseListView.ViewModel {
        .init(startingEntries: [
            ExerciseEntryViewData(
                UUID.init(),
                withTypeName: "Test Type",
                andSetNumber: 2,
                andWeight: 12.4,
                andReps: 8
            )
        ])
    }
}

#Preview {

    @Previewable @State var viewModel = ExerciseListView.ViewModel(
        startingEntries: [
            ExerciseEntryViewData(
                UUID.init(),
                withTypeName: "Test Type",
                andSetNumber: 3,
                andWeight: Float(5) + 0.5,
                andReps: 12
            ),
            ExerciseEntryViewData(
                UUID.init(),
                withTypeName: "Test Type",
                andSetNumber: 3,
                andWeight: Float(5) + 0.5,
                andReps: 12
            ),
        ]
    )

    ExerciseListView(viewModel: viewModel)
}
