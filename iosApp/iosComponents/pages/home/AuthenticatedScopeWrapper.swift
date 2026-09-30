import Foundation
import SwiftUI

public protocol AuthenticatedScopeWrapperFactroy {
    func create() -> AuthenticatedScopeWrapper
}

public protocol AuthenticatedScopeWrapper {
    func createExerciseViewModelFactory() -> ExerciseListViewModelFactory
}

struct PreviewAuthenticatedScopeWrapperFactory: AuthenticatedScopeWrapperFactroy {

    func create() -> AuthenticatedScopeWrapper {
        return PreviewAuthenticatedScopeWrapper()
    }

}

struct PreviewAuthenticatedScopeWrapper: AuthenticatedScopeWrapper {

    func createExerciseViewModelFactory() -> ExerciseListViewModelFactory {
        return PreviewExerciseListViewModelFactory()
    }

}

public extension EnvironmentValues {
    @Entry var authenticatedScopeWrapperFactory: any AuthenticatedScopeWrapperFactroy = PreviewAuthenticatedScopeWrapperFactory()
    @Entry var authenticatedScopeWrapper: any AuthenticatedScopeWrapper = PreviewAuthenticatedScopeWrapper()
}
