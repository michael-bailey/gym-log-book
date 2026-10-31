	//
	//  AddExerciseTypeForm.swift
	//  iosComponents
	//
	//  Created by michael bailey on 06/10/2026.
	//

import SwiftUI
import Observation

public struct AddExerciseTypeForm: View {
	
	@Environment(\.dismiss) var dismiss: DismissAction
	
	@State var viewModel: ViewModel
	
	public var body: some View {
		NavigationStack {
			Form {
				Picker("Equipment Class", selection: $viewModel.equipmentClass) {
					Text("None Selected...").tag(EquipmentClass.None)
					ForEach(EquipmentClass.selectable) { className in
						Text(className.displayName).tag(className)
					}
					Text("Undefined...")
						.tag(EquipmentClass.Undefined(viewModel.unknownEquipmentClassText))
				}
				if case .Undefined(_) = viewModel.equipmentClass {
					TextField("Unknown Class Name", text: $viewModel.unknownEquipmentClassText)
				}
				TextField("Name", text: $viewModel.name)
				
				Section {
					Button("Submit") {
						viewModel.submit()
						dismiss()
					}.disabled(!viewModel.isValid)
				}
			}.navigationTitle("Add New Type")
		}
	}
	
	public init(viewModel: ViewModel) {
		self.viewModel = viewModel
	}
	
	@Observable
	public class ViewModel {
		
		var name: String = ""
		var equipmentClass: EquipmentClass = .None
		
		var unknownEquipmentClassText: String {
			get {
				if case .Undefined(let text) = equipmentClass { return text }
				return ""
			}
			set {
				equipmentClass = .Undefined(newValue)
			}
		}
		
		public var delegate: Delegate? = nil
		
		var isValid: Bool {
			get { !name.isEmpty && equipmentClass != EquipmentClass.None && isUnknownEquipmentValid() }
		}
		
		func isUnknownEquipmentValid() -> Bool {
			if case .Undefined(let string) = equipmentClass {
				return !string.isEmpty
			} else { return true }
		}
		
		public init() {  }
		
		func submit() {
			delegate?.submit(withName: name, andClass: equipmentClass)
		}
		
		public protocol Delegate {
			func submit(
				withName name: String,
				andClass equipmentClass: EquipmentClass,
			)
		}
	}
}



#Preview {
	
	@Previewable @State var viewModel = AddExerciseTypeForm.ViewModel()
	
	AddExerciseTypeForm(viewModel: viewModel)
	#if os(macOS)
		.padding()
	#endif
}
