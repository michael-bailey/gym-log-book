import SwiftUI
import Observation

public struct ExerciseTypeView: View {

    let data: ExerciseEntryViewData

    public var body: some View {
        VStack {
            Text(verbatim: "\(data.exerciseTypeName)")
                .frame(alignment: .leading)
            HStack {
                Text("Set: \(data.setNumber)")
                Text("Weight: \(String(format: "%.2f", data.weight))")
                Text("Reps: \(data.reps)")
            }
        }
    }
}

#Preview {

    ExerciseListEntryView(data: ExerciseEntryViewData(
        UUID.init(),
        withTypeName: "Test Type",
        andSetNumber: 3,
        andWeight: Float(5) + 0.5,
        andReps: 12
    )).padding()

}
