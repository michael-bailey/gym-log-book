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

    private let viewModel: ILoginPageViewModel

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

    public init(
        _ authComponent: IAuthenticationComponent = AuthenticationComponent.shared,
        username: String = "",
        password: String = ""
    ) {
        self.viewModel = authComponent.createLoginPageViewModel()
        self.errorText = ""
        self.username = username
        self.password = password
    }

    public func submit() {
        viewModel.submit()
    }
}
