import Foundation
import Swinject


import iosComponents
import gym_client_kt


class HomeAssembly: Assembly {
    func assemble(container: Swinject.Container) {

        container.register(AuthenticatedScope.self) { _container in
            return AuthenticatedScope()
        }
        .inObjectScope(.weak)

        container.register(IExerciseEntryTabViewModel.self) { _container in
            let authScope = container.resolve(AuthenticatedScope.self)!
            return authScope.exerciseEntryListViewModel
        }

        container.register(ExerciseListView.ViewModel.self) { container in
            let authComponent = container.resolve(AuthenticatedScope.self)!
            let viewModel = authComponent.exerciseEntryListViewModel
            let observable = ExerciseListView.ViewModel()

            let _ = ExerciseListAdapter(viewModel: viewModel, observable: observable)

            return observable
        }

        container.register(AddExerciseEntryFormView.ViewModel.self) { container in
            let authComponent = container.resolve(AuthenticatedScope.self)!
            let viewModel = authComponent.exerciseEntryListViewModel

            let observable = AddExerciseEntryFormView.ViewModel(date: Date.now, selectables: [])

            let _ = AddExerciseEntryFormAdapter(viewModel: viewModel, observable: observable)

            return observable
        }


    }
}
