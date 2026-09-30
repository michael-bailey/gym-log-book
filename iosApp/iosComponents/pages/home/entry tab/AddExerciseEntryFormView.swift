//
//  AddExerciseEntryFormView.swift
//  iosComponents
//
//  Created by michael bailey on 26/09/2026.
//

import SwiftUI
import Observation

public struct AddExerciseEntryFormView: View {

    @State var viewModel: ViewModel

    public var body: some View {
        Form {
            Section {
                Picker(selection: $viewModel.selection, label: Text("Picker")) {
                    ForEach(viewModel.selectables, id: \.self) { it in
                        Text("\(it)").tag(it)
                    }
                    Text("No Selection").tag(0)
                }
                DatePicker("Date", selection: $viewModel.date)
            }
            Section {
                TextField("Set", value: $viewModel.setNumber, formatter: NumberFormatter())
                HStack {
                    TextField("Weight", value: $viewModel.weight, formatter: NumberFormatter())
                    Text("kg")
                }
                TextField("Reps", value: $viewModel.reps, formatter: NumberFormatter())
            }
        }
        .formStyle(.grouped)
    }

    public init(viewModel: ViewModel) {
        self.viewModel = viewModel
    }

    @Observable
    public class ViewModel {

        public var date: Date
        public var selection: Int = 0
        public var selectables: [Int]

        public var setNumber: Int = 0
        public var weight: Float = 0
        public var reps: Int = 0


        public init(
            date: Date,
            selection: Int = 0,
            selectables: [Int]
        ) {
            self.date = date
            self.selection = selection
            self.selectables = selectables
        }
    }
}

#Preview {

    @Previewable @State var viewModel: AddExerciseEntryFormView.ViewModel = .init(
        date: Date.now,
        selection: 0,
        selectables: [1, 2, 3, 4, 5, 6]
    )

    AddExerciseEntryFormView(viewModel: viewModel).padding()
}
