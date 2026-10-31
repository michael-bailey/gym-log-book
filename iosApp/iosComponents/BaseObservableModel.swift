//
//  baseViewModelWrapper.swift
//  iosComponents
//
//  Created by michael bailey on 19/09/2026.
//

import Foundation
import Combine

@Observable
@MainActor
open class BaseObservableModel {

    private let taskBag = TaskBag()

    public init() {
    }

    deinit {
        taskBag.cancelAll()
    }

    public func track<T, F: Error>(_ flow: any AsyncSequence<T, F>, onEach: @escaping (T) -> Void) {
        let task = Task {
            do {
                for try await value in flow {
                    onEach(value)
                }
            } catch {
                // StateFlow doesn't actually throw in practice
            }
        }
        taskBag.add(task)
    }

    private final class TaskBag {
        private var tasks: [Task<Void, Never>] = []

        func add(_ task: Task<Void, Never>) {
            tasks.append(task)
        }

        func cancelAll() {
            tasks.forEach {
                $0.cancel()
            }
        }
    }
}
