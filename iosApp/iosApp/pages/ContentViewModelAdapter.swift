//
//  RealContentViewModelFactory.swift
//  iosApp
//
//  Created by michael bailey on 29/09/2026.
//

import Foundation
import iosComponents
import gym_client_kt

public class ContentViewModelAdapter: BaseObservableModel, ContentView.ViewModel.Delegate {

    let viewModel: ApplicationViewModel
    weak var observable: ContentView.ViewModel?

    init(
        viewModel: ApplicationViewModel,
        observable: ContentView.ViewModel,
        ) {

        print("ContentViewModelAdapter: init")

        self.viewModel = viewModel
        self.observable = observable

        super.init()

        self.observable?.delegate = self

        self.track(self.viewModel.isLoginWindowShown) { [weak observable] it in
            if (it.boolValue) {
                observable?.displayedPage = .Login
            } else {
                observable?.displayedPage = .Home
            }
        }
    }

    isolated deinit {
        print("ContentViewModelAdapter: Deinit")
    }
}
