	//
	//  AddExerciseEntryFormAdapter.swift
	//  iosApp
	//
	//  Created by michael bailey on 01/10/2026.
	//

import Foundation

import iosComponents
import gym_client_kt

@MainActor
class AddExerciseTypeFormAdapter: BaseObservableModel, AddExerciseTypeForm.ViewModel.Delegate {

	let viewModel: IExerciseTypeTabViewModel
	weak var observable: AddExerciseTypeForm.ViewModel?
	
	init(
		viewModel: IExerciseTypeTabViewModel,
		observable: AddExerciseTypeForm.ViewModel
	) {
		self.viewModel = viewModel
		self.observable = observable
		
		super.init()
		
		self.observable?.delegate = self
	}
	
	func submit(
		withName name: String,
		andClass equipmentClass: iosComponents.EquipmentClass
	) {
		self.viewModel
			.submitCreateTypeForm(
				equipmentClass: self.convertToKotlin(equipmentClass: equipmentClass),
				name: name
			)
	}
	
	func convertToKotlin(equipmentClass: EquipmentClass) -> Gym_log_book_sharedEquipmentClass {
		switch equipmentClass {
			case .Calisthenics:
				Gym_log_book_sharedEquipmentClass.Calisthenics()
			case .Machine:
				Gym_log_book_sharedEquipmentClass.Machine()
			case .UserWeightMachine:
				Gym_log_book_sharedEquipmentClass.UserWeightMachine()
			case .FreeWeight:
				Gym_log_book_sharedEquipmentClass.FreeWeight()
			case .None:
				Gym_log_book_sharedEquipmentClass.None()
			case .Undefined(let text):
				Gym_log_book_sharedEquipmentClass.Undefined(text: text)
		}
	}
}
