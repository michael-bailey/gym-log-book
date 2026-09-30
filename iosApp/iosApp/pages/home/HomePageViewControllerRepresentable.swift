//
//  HomePageViewControllerRepresentable.swift
//  iosApp
//
//  Created by michael bailey on 28/09/2026.
//

#if os(iOS)
import SwiftUI

import gym_client_kt
import UIKit

struct HomePageViewControllerRepresentable: UIViewControllerRepresentable {
    typealias UIViewControllerType = UIViewController

    func makeUIViewController(context: Context) -> UIViewController {
        HomeViewControllerKt.create()
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {

    }
}

#endif
