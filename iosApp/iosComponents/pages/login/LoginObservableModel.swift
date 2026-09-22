//
//  LoginPageViewModel.swift
//  iosComponents
//
//  Created by michael bailey on 19/09/2026.
//

import Foundation
import Observation
import gym_client_kt

@Observable
public class LoginObservableModel: BaseObservableModel, ViewModel {

    private let authComponent: AuthenticationComponent

    private let viewModel: LoginPageViewModel

    public var username: String {
        didSet {
            viewModel.onUsernameChanged(text: username)
        }
    }
    public var password: String {
        didSet {
            viewModel.onPasswordChanged(text: password)
        }
    }

    public let errorText: String?

    public init(username: String = "", password: String = "") {
        self.authComponent = AuthenticationComponent.shared

        self.errorText = "errorText"
        self.username = username
        self.password = password

        self.viewModel = authComponent.createLoginPageViewModel()
    }

    public func submit() {
        viewModel.submit()
    }
}
