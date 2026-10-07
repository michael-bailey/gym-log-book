	//
	//  ExerciseListView.swift
	//  iosComponents
	//
	//  Created by michael bailey on 25/09/2026.
	//

import SwiftUI
import Swinject

public struct ExerciseListView: View {
	
	@Environment(\.container) var container: Container
	
	@State var viewModel: ViewModel
	
	public var body: some View {
		NavigationStack(path: $viewModel.path, root: {
			List(viewModel.allEntries, id: \.id) { entry in
				ExerciseListEntryView(data: entry)
			}.navigationTitle(
				"Exercise Entries"
			).toolbar {
				AddMenu()
			}.sheet(isPresented: $viewModel.isAddEntryShown) {
				self.AddExerciseEntryForm()
			}
#if os(iOS)
				.navigationBarTitleDisplayMode(.large)
#endif
		})
	}
	
	public init(viewModel: ViewModel) {
		self.viewModel = viewModel
	}
	
	@ViewBuilder
	func AddMenu() -> some View {
		Menu {
		} label: {
			Image(systemName: "plus")
		} primaryAction: {
			viewModel.isAddEntryShown = true
		}
	}
	
	@ViewBuilder
	func AddExerciseEntryForm() -> some View {
		AddExerciseEntryFormView(
			viewModel: container.resolve(
				AddExerciseEntryFormView.ViewModel.self
			) ?? AddExerciseEntryFormView.ViewModel(date: Date.now, selectables: [])
		)
	}
	
	@Observable
	public class ViewModel {
		
		public var allEntries: [ExerciseEntryViewData]
		public var delegate: Delegate? = nil
		
		var path: [EntryPage] = []
		var isAddEntryShown: Bool = false
		
		public init(startingEntries: [ExerciseEntryViewData] = []) {
			self.allEntries = startingEntries
		}
		
		enum EntryPage: Hashable, Equatable, Codable {
			case Detail(entry: ExerciseEntryViewData)
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
