//
//  HomePage.swift
//  iosComponents
//
//  Created by michael bailey on 24/09/2026.
//

import SwiftUI
import gym_client_kt

struct HomePageViewControllerRepresentable: UIViewControllerRepresentable {
    typealias UIViewControllerType = UIViewController

    func makeUIViewController(context: Context) -> UIViewController {
        HomeViewControllerKt.create()
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {

    }
}


public struct HomePage: View {
    public var body: some View {
        HomePageViewControllerRepresentable()
    }

    public init() {
    }
}

public protocol ViewModel: Observable, AnyObject {
    var errorText: String? { get }

    var username: String { get set }
    var password: String { get set }

    func submit()
}

#Preview {
    HomePage()
}
