//
//  Environment+container.swift
//  iosComponents
//
//  Created by michael bailey on 01/10/2026.
//

import Foundation
import SwiftUI
import Swinject

public extension EnvironmentValues {
    @Entry var container: Container = Container()
}

