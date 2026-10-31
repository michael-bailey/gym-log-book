	//
	//  ContentView.swift
	//  iosApp
	//
	//  Created by michael bailey on 29/09/2026.
	//

import SwiftUI
import Swinject

public struct ContentView: View {
	
	@Environment(\.container) var container: Container
	
	@Environment(\.loginViewModelFactory)
	var loginViewModelFactory: LoginViewModelFactory
	
	@Environment(\.authenticatedScopeWrapperFactory)
	var authenticatedScopeWrapperFactory: AuthenticatedScopeWrapperFactroy
	
	@State var viewModel: ContentView.ViewModel
	
	public var body: some View {
		Group {
			switch (viewModel.displayedPage) {
				case .Home:
					let factory = authenticatedScopeWrapperFactory.create()
					let exerciseViewModelFactory = factory.createExerciseViewModelFactory()
					HomePage()
						.environment(\.exerciseListViewModelFactory, exerciseViewModelFactory)
				case .Login:
					LoginPage(
						viewModel: container
							.resolve(LoginPage.ViewModel.self) ?? LoginPage.ViewModel()
					)
			}
		}
	}
	
	public init(viewModel: ContentView.ViewModel) {
		self.viewModel = viewModel
	}
	
	@Observable
	public class ViewModel {
		
		public var displayedPage: Page
		
		public var delegate: Delegate?
		
		public init(displayedPage: Page = .Login) {
			self.displayedPage = displayedPage
		}
		
		public protocol Delegate {
			
		}
	}
	
	public enum Page {
		case Login
		case Home
	}
}

public protocol ContentViewModelFactory {
	func create() -> ContentView.ViewModel
}

public extension EnvironmentValues {
	@Entry var contentViewModelFactory: any ContentViewModelFactory =
	PreviewContentViewModelFactory()
}

struct PreviewContentViewModelFactory: ContentViewModelFactory {
	func create() -> ContentView.ViewModel {
		.init()
	}
}

#Preview {
	@Previewable @State var viewModel = ContentView.ViewModel()
	VStack {
		ContentView(viewModel: viewModel)
		Button {
			switch (viewModel.displayedPage) {
				case .Login:
					viewModel.displayedPage = .Home
				case .Home:
					viewModel.displayedPage = .Login
			}
		} label: {
			Text(verbatim: "Swap Page")
		}
	}
}
