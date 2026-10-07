//
//  ExerciseEntryViewData.swift
//  iosComponents
//
//  Created by michael bailey on 27/09/2026.
//

import Foundation
import gym_client_kt

public struct ExerciseEntryViewData: Identifiable, Hashable, Equatable, Codable {

    public let id: UUID
    public let exerciseTypeName: String

    public let setNumber: Int
    public let weight: Float
    public let reps: Int


    public init(
        _ id: UUID,
        withTypeName exerciseTypeName: String,
        andSetNumber setNumber: Int,
        andWeight weight: Float,
        andReps reps: Int
    ) {
        self.id = id
        self.exerciseTypeName = exerciseTypeName
        self.setNumber = setNumber
        self.weight = weight
        self.reps = reps
    }

}
