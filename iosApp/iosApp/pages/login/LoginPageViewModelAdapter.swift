//
//  LoginObservableModel.swift
//  iosApp
//
//  Created by michael bailey on 25/09/2026.
//

import Observation
import iosComponents
import gym_client_kt

class LoginPageViewModelAdapter: BaseObservableModel, LoginPage.ViewModel.Delegate {

    let viewModel: ILoginPageViewModel
    weak var observable: LoginPage.ViewModel?

    init(viewModel: ILoginPageViewModel, observable: LoginPage.ViewModel) {
        self.observable = observable
        self.viewModel = viewModel

        super.init()

        observable.delegate = self
    }

    func didSetUsername(text: String) {
        viewModel.onUsernameChanged(text: text)
    }

    func didSetPassword(text: String) {
        viewModel.onPasswordChanged(text: text)
    }

    func onClick() {
        viewModel.submit()
    }
}
