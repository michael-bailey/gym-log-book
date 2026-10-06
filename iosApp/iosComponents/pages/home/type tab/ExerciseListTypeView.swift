import SwiftUI
import Observation

public struct ExerciseTypeListTypeView: View {
	
	let data: ExerciseTypeViewData
	
	public var body: some View {
		VStack {
			Text(verbatim: "\(data.id)")
				.frame(alignment: .leading)
			 
			Text("name: \(data.exerciseTypeName)").frame(alignment: .leading)
			Text("class: \(data.exerciseClass)").frame(alignment: .leading)
			
		}
	}
}

#Preview {
	ExerciseTypeListTypeView(
		data: ExerciseTypeViewData(
			UUID(),
			withName: "Test type",
			andExerciseClass: "Free Weight"
		)
	).padding()
}
