	//
	//  ExerciseTypeListView.swift
	//  iosComponents
	//
	//  Created by michael bailey on 02/10/2026.
	//

import SwiftUI
import Swinject

public struct ExerciseTypeListView: View {
	
	@Environment(\.container) var container: Container
	
	@State var viewModel: ViewModel
	
	public var body: some View {
		NavigationStack {
			List(viewModel.exerciseTypeList) { data in
				ExerciseTypeListTypeView(data: data)
			}.toolbar {
				addMenu()
			}.navigationTitle(
				"Exercise Types"
			).sheet(isPresented: $viewModel.isAddExerciseTypeFormShown) {
				AddExerciseTypeForm(viewModel: container.resolve(AddExerciseTypeForm.ViewModel.self) ?? .init())
			}
		}
	}
	
	public init(viewModel: ViewModel = ViewModel()) {
		self.viewModel = viewModel
	}
	
	@ViewBuilder
	func addMenu() -> some View {
		Menu {
		} label: {
			Image(systemName: "plus.circle")
		} primaryAction: {
			print("showing sheet")
			viewModel.isAddExerciseTypeFormShown = true
		}
	}
	
	@Observable
	public class ViewModel {
		
		public var exerciseTypeList: [ExerciseTypeViewData]
		public var delegate: Delegate? = nil
		
		var isAddExerciseTypeFormShown: Bool = false
		
		public init(
			exerciseTypeList: [ExerciseTypeViewData] = []
		) {
			self.exerciseTypeList = exerciseTypeList
		}
		
		public protocol Delegate {
			
		}
	}
}

#Preview {
	
	@Previewable @State var exerciseTypeList: [ExerciseTypeViewData] = [
		ExerciseTypeViewData(
			UUID(),
			withName: "Test type",
			andExerciseClass: "Free Weight"
		)
	]
	
	ExerciseTypeListView(
		viewModel: ExerciseTypeListView.ViewModel(exerciseTypeList: exerciseTypeList)
	)
}
