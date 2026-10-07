	//
	//  AddExerciseEntryFormView.swift
	//  iosComponents
	//
	//  Created by michael bailey on 26/09/2026.
	//

import SwiftUI
import Observation
import Swinject

public struct AddExerciseEntryFormView: View {
	
	@Environment(\.dismiss) var dismiss: DismissAction
	
	@State var viewModel: ViewModel
	
	public var body: some View {
		NavigationStack {
			Form {
				Section {
					Picker(selection: $viewModel.selection, label: Text("Exercise Type")) {
						ForEach(viewModel.selectables, id: \.id) { it in
							Text("\(it.name)").tag(it.id)
						}
						Text("No Selection").tag(UUID.NIL)
					}
				}
				FormFields()
				Section {
					Button("Submit") {
						self.submit()
						dismiss()
					}.disabled(!viewModel.isValid)
				}
			}.formStyle(.grouped).navigationTitle("Add Entry")
		}
	}
	
	public init(viewModel: ViewModel) {
		self.viewModel = viewModel
	}
	
	private func submit() {
		print("Submitting form")
		self.viewModel.submit()
	}
	
	@ViewBuilder
	func FormFields() -> some View {
		
#if os(macOS)
		Section {
			TextField("Set Number", value: $viewModel.setNumber,
								format: .number.precision(
									.fractionLength(0)).sign(strategy: .never)
			)
			TextField("Weight", value: $viewModel.weight,
								format: .number.precision(
									.fractionLength(2)).sign(strategy: .never)
			)
			TextField("Repetitions", value: $viewModel.reps,
								format: .number.precision(
									.fractionLength(0)).sign(strategy: .never)
			)
		}
#else
		Section {
			TextField("Set Number", value: $viewModel.setNumber,
								format: .number.precision(
									.fractionLength(0)).sign(strategy: .never)
			).keyboardType(.decimalPad)
			TextField("Weight", value: $viewModel.weight,
								format: .number.precision(
									.fractionLength(2)).sign(strategy: .never)
			).keyboardType(.decimalPad)
			TextField("Repetitions", value: $viewModel.reps,
								format: .number.precision(
									.fractionLength(0)).sign(strategy: .never)
			).keyboardType(.decimalPad)
		}
#endif
	}
	
	@Observable
	public class ViewModel {
		
		public var date: Date
		public var selection: UUID
		public var selectables: [Selectable]
		
		public var setNumber: Int32? = nil
		public var weight: Double? = nil
		public var reps: Int32? = nil
		
		public var isValid: Bool {
			get { setNumber != nil && weight != nil && reps != nil && selection != UUID.NIL }
		}
		
		public var delegate: Delegate? = nil
		
		public init(
			date: Date = Date.now,
			selection: UUID = UUID.NIL,
			selectables: [Selectable]
		) {
			self.date = date
			self.selection = selection
			self.selectables = selectables
		}
		
		public convenience init() {
			self.init(
				date: Date.now,
				selection: UUID.NIL,
				selectables: []
			)
		}
		
		func submit() {
			delegate?.submit(
				exerciseType: self.selection,
				setNumber: self.setNumber!,
				weight: self.weight!,
				reps: self.reps!,
			)
		}
		
		public protocol Delegate {
			func submit(
				exerciseType: UUID,
				setNumber: Int32,
				weight: Double,
				reps: Int32
			)
		}
	}
}

public struct Selectable {
	let id: UUID
	let name: String
	
	public init(id: UUID, name: String) {
		self.id = id
		self.name = name
	}
}

#Preview {
	
	@Previewable @State var container = Container() { container in
		
	}
	
	@Previewable @State var viewModel: AddExerciseEntryFormView.ViewModel = .init(
		date: Date.now,
		selection: UUID.NIL,
		selectables: [
			Selectable(id: UUID(), name: "Test Type 1"),
			Selectable(id: UUID(), name: "Test Type 2"),
			Selectable(id: UUID(), name: "Test Type 3"),
			Selectable(id: UUID(), name: "Test Type 4"),
			Selectable(id: UUID(), name: "Test Type 5"),
		]
	)
	
	AddExerciseEntryFormView(viewModel: viewModel)
#if os(macOS)
		.padding()
#endif
}
