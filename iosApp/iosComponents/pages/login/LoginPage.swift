//
//  LoginView.swift
//  iosApp
//
//  Created by michael bailey on 17/09/2026.
//


import SwiftUI
import Observation

public struct LoginPage<VM>: View where VM: ViewModel & Observable {

    @Bindable var viewModel: VM

    public var body: some View {
        Form {
            TextField("Username", text: $viewModel.username)
            TextField("Password", text: $viewModel.password)
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
        .padding()
    }

    public init(
        viewModel: VM,
        ) {
        self.viewModel = viewModel
    }

    private func onClick() {
        viewModel.submit()
    }
}

public protocol ViewModel: Observable, AnyObject {
    var errorText: String? { get }

    var username: String { get set }
    var password: String { get set }

    func submit()
}

@Observable
public class TestViewModel: ViewModel {

    public var username: String {
        didSet {
            print(username)
        }
    }
    public var password: String {
        didSet {
            print(password)
        }
    }

    public let errorText: String? = nil

    public init(username: String = "", password: String = "") {
        self.username = username
        self.password = password
    }

    public func submit() {
        print("Logging in")
    }
}


#Preview {
    let viewModel = TestViewModel()
    LoginPage(viewModel: viewModel)
}
