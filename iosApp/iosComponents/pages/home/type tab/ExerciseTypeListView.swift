	//
	//  ExerciseTypeListView.swift
	//  iosComponents
	//
	//  Created by michael bailey on 02/10/2026.
	//

import SwiftUI

public struct ExerciseTypeListView: View {
	
	@State var viewModel: ViewModel
	
	public var body: some View {
		List(viewModel.exerciseTypeList) { data in
			ExerciseTypeListTypeView(data: data)
		}
	}
	
	public init(viewModel: ViewModel = ViewModel()) {
		self.viewModel = viewModel
	}
	
	public class ViewModel {
		
		public var delegate: Delegate? = nil
		
		public var exerciseTypeList: [ExerciseTypeViewData]
		
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
