//
//  LoginAssembly.swift
//  iosApp
//
//  Created by michael bailey on 30/09/2026.
//

import Foundation
import Swinject

import iosComponents

import gym_client_kt

class LoginAssembly: Assembly {
    func assemble(container: Swinject.Container) {
        container.register(AuthenticationComponent.self) { container in
            return AuthenticationComponent.shared
        }

        container.register(LoginPageViewModel.self) { container in
            let authComponent = container.resolve(AuthenticationComponent.self)!
            return authComponent.createLoginPageViewModel()
        }

        container.register(LoginPage.ViewModel.self) { container in
            return LoginPage.ViewModel()
        }
        .initCompleted { container, vm in
            let _ = LoginPageViewModelAdapter(viewModel: container.resolve(LoginPageViewModel.self)!, observable: vm)
        }
        .inObjectScope(.weak)
    }
}
