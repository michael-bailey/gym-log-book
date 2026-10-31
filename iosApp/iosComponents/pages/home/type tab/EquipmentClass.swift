//
//  EquipmentClass.swift
//  iosApp
//
//  Created by michael bailey on 07/10/2026.
//


public enum EquipmentClass: Codable, Hashable, Equatable, Identifiable {
	
	public var id: Self { self }
	
	case Machine
	case UserWeightMachine
	case Calisthenics
	case FreeWeight
	case None
	case Undefined(String)
	
	var displayName: String {
		switch self {
			case .Machine: "Machine"
			case .UserWeightMachine: "Body-weight machine"
			case .Calisthenics: "Calisthenics"
			case .FreeWeight: "Free weight"
			case .None: "None"
			case .Undefined(let text): text
		}
	}

	static let selectable: [EquipmentClass] = [
		.Machine,
		.UserWeightMachine,
		.Calisthenics,
		.FreeWeight,
	]

}
