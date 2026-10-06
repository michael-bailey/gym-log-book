//
//  ExerciseEntryViewData.swift
//  iosComponents
//
//  Created by michael bailey on 27/09/2026.
//

import Foundation
import gym_client_kt

public struct ExerciseTypeViewData: Identifiable {

    public let id: UUID
    public let exerciseTypeName: String

    public init(
        _ id: UUID,
        withName exerciseTypeName: String,
    ) {
        self.id = id
        self.exerciseTypeName = exerciseTypeName
    }
}
