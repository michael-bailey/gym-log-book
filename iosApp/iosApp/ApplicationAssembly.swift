//
//  ApplicationAssembly.swift
//  iosApp
//
//  Created by michael bailey on 01/10/2026.
//

import Foundation

import Swinject

import iosComponents
import gym_client_kt


class ApplicationAssembly: Assembly {
    func assemble(container: Swinject.Container) {
        container.register(ApplicationComponent.self) { _container in
            return ApplicationComponent.shared
        }

        container.register(ContentView.ViewModel.self) { container in
            let appComponent = container.resolve(ApplicationComponent.self)!
            let viewModel = appComponent.appViewModel
            let observable = ContentView.ViewModel()

            _ = ContentViewModelAdapter(viewModel: viewModel, observable: observable)

            return observable
        }
    }
}
