import SwiftUI
import Observation

public struct ExerciseTypeListTypeView: View {
	
	let data: ExerciseTypeViewData
	
	public var body: some View {
		HStack {
			Image(systemName: "figure.run.circle")
				.resizable()
				.scaledToFit()
				.frame(width: 40, height: 40)
			VStack(alignment: .leading) {
				Text(verbatim: "ID: \(data.id)").font(.default.scaled(by: 0.75))
				Text("name: \(data.exerciseTypeName)")
				Text("class: \(data.exerciseClass)")
			}
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
