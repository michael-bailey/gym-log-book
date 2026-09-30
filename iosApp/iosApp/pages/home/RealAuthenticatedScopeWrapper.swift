import Foundation
import iosComponents
import gym_client_kt

class RealAuthenticatedScopeWrapper: AuthenticatedScopeWrapper {

    let authenticatedScope = AuthenticatedScope()

    func createExerciseViewModelFactory() -> any ExerciseListViewModelFactory {
        return RealExerciseViewModelFactory(authenticatedComponent: authenticatedScope)
    }
}

class RealAuthenticatedScopeWrapperFactory: AuthenticatedScopeWrapperFactroy {
    func create() -> any iosComponents.AuthenticatedScopeWrapper {
        return RealAuthenticatedScopeWrapper()
    }
}
