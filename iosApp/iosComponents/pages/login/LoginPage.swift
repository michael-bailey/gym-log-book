//
//  LoginView.swift
//  iosApp
//
//  Created by michael bailey on 17/09/2026.
//


import SwiftUI
import Observation

public struct LoginPage: View {

    @Environment(\.scenePhase) private var scenePhase

    @State var viewModel: LoginPage.ViewModel

    public var body: some View {
        Form {
            TextField("Username", text: $viewModel.username)
                #if os(iOS)
                .textInputAutocapitalization(.never)
                #endif
            SecureField("Password", text: $viewModel.password)
                #if os(iOS)
                .textInputAutocapitalization(.never)
                #endif
            HStack {
                Button {
                    viewModel.onClick()
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

    public init(viewModel: LoginPage.ViewModel) {
        self.viewModel = viewModel
    }
	
	@Observable
    public class ViewModel {

        public var delegate: Delegate? = nil
		
		public var username: String {
            didSet {
                delegate?.didSetUsername(text: username)
            }
		}

        public var password: String {
            didSet {
                delegate?.didSetPassword(text: password)
            }
        }

        public init() {
            self.username = ""
            self.password = ""

            print("VM init: \(self) @ \(Unmanaged.passUnretained(self).toOpaque())")
		}

        deinit {
            print("VM deinit:  \(Unmanaged.passUnretained(self).toOpaque())")
        }

        open func onClick() {
            print("Login Page click")
            delegate?.onClick()
		}

        public protocol Delegate {
            func didSetUsername(text: String)
            func didSetPassword(text: String)

            func onClick()
		}
	}
}

public protocol LoginViewModelFactory {
    func create() -> LoginPage.ViewModel
}

internal class PreviewLoginViewModelFactory: LoginViewModelFactory {
    func create() -> LoginPage.ViewModel {
        .init()
    }
}

public extension EnvironmentValues {
    @Entry var loginViewModelFactory: any LoginViewModelFactory = PreviewLoginViewModelFactory()
}

#Preview {
    @Previewable @State var viewModel = PreviewLoginViewModelFactory().create()
    LoginPage(viewModel: viewModel).padding()
}
