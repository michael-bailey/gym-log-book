import SwiftUI
import Observation

import iosComponents
import gym_client_kt

@main struct MyApp: App {

    private let contentViewModelFactory: any ContentViewModelFactory
    private let loginViewModelFactory: any LoginViewModelFactory
    private let authenticatedScopeWrapperFactroy: any AuthenticatedScopeWrapperFactroy

    var body: some Scene {
        WindowGroup {
            ContentView(viewModel: contentViewModelFactory.create())
                .environment(\.loginViewModelFactory, loginViewModelFactory)
                .environment(\.authenticatedScopeWrapperFactory, authenticatedScopeWrapperFactroy)
        }
    }

    init() {
        MainKt.doInitKoin()

        contentViewModelFactory = RealContentViewModelFactory()
        loginViewModelFactory = RealLoginViewModelFactory()
        authenticatedScopeWrapperFactroy = RealAuthenticatedScopeWrapperFactory()
    }
}

