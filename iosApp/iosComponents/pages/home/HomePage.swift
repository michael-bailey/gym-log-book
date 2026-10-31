	//
	//  HomePage.swift
	//  iosComponents
	//
	//  Created by michael bailey on 24/09/2026.
	//

import SwiftUI
import Swinject

public struct HomePage: View {
	
	@Environment(\.container) private var container: Container
	
	@State public var viewModel: ViewModel
	
	public var body: some View {
		TabView(selection: $viewModel.selectedTab) {
			ExerciseListView(
				viewModel: container
					.resolve(
						ExerciseListView.ViewModel.self
					) ?? ExerciseListView.ViewModel()
			).tabItem {
				Label("Entries", systemImage: "star")
			}.tag(ViewModel.Tab.Exercises)
			ExerciseTypeListView(
				viewModel: container
					.resolve(
						ExerciseTypeListView.ViewModel.self
					) ?? ExerciseTypeListView.ViewModel()
			).tabItem {
				Label("Types", systemImage: "plus")
			}.tag(
				ViewModel.Tab.Types
			).navigationTitle("Types")
			Text("Testing.").tabItem {
				Label("Testing", systemImage: "scalemass")
			}
			.tag(ViewModel.Tab.Test)
		}.tabViewStyle(.sidebarAdaptable)
		
		
	}
	
	public init(viewModel: ViewModel = .init()) {
		self.viewModel = viewModel
	}
	
	@ViewBuilder
	private func AddMenu() -> some View {
		Menu {
			Button {
				viewModel.isAddEntryShown = true
			} label: {
				Label("Add Entry", systemImage: "figure.walk")
			}
			Button {
				viewModel.isAddEntryShown = true
			} label: {
				Label("Add Type", systemImage: "figure.walk.circle")
			}
		} label: {
			Image(systemName: "plus")
		}
	}
	
	@Observable
	public class ViewModel {
		var isAddEntryShown: Bool = false
		
		var selectedTab: Tab = .Exercises
		
		public init() {
		}
		
		enum Tab {
			case Exercises
			case Types
			case Test
		}
		
		public protocol Delegate {
		}
	}
}

public protocol HomePageViewModelFactory {
	func create() -> HomePage.ViewModel
}

internal class PreviewHomePageViewModelFactory: HomePageViewModelFactory {
	func create() -> HomePage.ViewModel {
		.init()
	}
}

public extension EnvironmentValues {
	@Entry var homePageViewModelFactory: any HomePageViewModelFactory = PreviewHomePageViewModelFactory()
}

#Preview {
	@Previewable @State var viewModel = PreviewHomePageViewModelFactory().create()
	
	HomePage(viewModel: viewModel)
}
