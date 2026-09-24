//
//  LoginView.swift
//  iosApp
//
//  Created by michael bailey on 17/09/2026.
//


import SwiftUI
import Observation
import gym_client_kt

public struct LoginPage: View {

	@Bindable var viewModel: ViewModel

    public var body: some View {
        Form {
			TextField("Username", text: $viewModel.username).textInputAutocapitalization(.never)
			SecureField("Password", text: $viewModel.password).textInputAutocapitalization(.never)
            HStack {
                Button {
                    onClick()
                } label: {
                    Label {
                        Text(verbatim: "Login")
                    } icon: {
                        Image(systemName: "person.badge.key")
                    }
                }
            }
        }
    }

    public init(
		viewModel: ViewModel,
		) {
        self.viewModel = viewModel
    }

    private func onClick() {
        viewModel.submit()
    }

	@Observable
		///# Login View Model
		///A view model for mapping gym view model to swift UI
	open class ViewModel: Observable, AnyObject {
		internal let viewModel: ILoginPageViewModel

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

		public var errorText: String?

		public init(
			viewModel: ILoginPageViewModel,
			username: String = "",
			password: String = ""
		) {
			self.viewModel = viewModel
			self.errorText = ""
			self.username = username
			self.password = password
		}

		public func submit() {
			viewModel.submit()
		}
	}
}
