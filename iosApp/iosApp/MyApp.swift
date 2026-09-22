import SwiftUI
import Observation

import iosComponents
import gym_client_kt

@main struct MyApp: App {

    let viewModel: ViewModel
	
    var body: some Scene {
        WindowGroup {
            switch (viewModel.displayedPage) {
            case .Home:
                ContentView()
            case .Login:
                LoginPage(viewModel: LoginObservableModel())
            }
        }
    }

    init() {
        MainKt.doInitKoin()
        viewModel = ViewModel()
    }

    @Observable
    @MainActor
    class ViewModel: BaseObservableModel {
        private let applicationComponent: ApplicationComponent
        private let viewModel: ApplicationViewModel

        var displayedPage: Page = .Login

        override init() {
            applicationComponent = ApplicationComponent.shared
            viewModel = applicationComponent.appViewModel

            super.init()

            track(viewModel.isLoginWindowShown) { [weak self] in
                if ($0) as! Bool {
                    self?.displayedPage = .Login
                } else {
                    self?.displayedPage = .Home
                }
            }
        }

        enum Page {
            case Login
            case Home
        }
    }
}
