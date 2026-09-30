//
//  HomePage.swift
//  iosComponents
//
//  Created by michael bailey on 24/09/2026.
//

import SwiftUI

public struct HomePage: View {

    @Environment(\.exerciseListViewModelFactory) private var exerciseFactory: ExerciseListViewModelFactory

    @State public var viewModel: ViewModel

    public var body: some View {
        TabView(selection: $viewModel.selectedTab) {
            ExerciseListView(viewModel: exerciseFactory.create()).tabItem {
                Label("Entries", systemImage: "star")
            }
            .tag(ViewModel.Tab.Exercises)
            Text("Types should be here.").tabItem {
                Label("Types", systemImage: "plus")
            }
            .tag(ViewModel.Tab.Types)
            Text("Testing.").tabItem {
                Label("Testing", systemImage: "scalemass")
            }
            .tag(ViewModel.Tab.Test)
        }
        .toolbarVisibility(.visible, for: .automatic)
        .toolbar() {
            AddMenu()
        }
        .sheet(isPresented: $viewModel.isAddEntryShown) {
            AddExerciseEntryFormView(viewModel: .init(date: Date.now, selectables: [1, 2, 3, 4, 5, 6, 7, 8, 9, 10]))
        }
    }

    public init(viewModel: ViewModel = .init()) {
        self.viewModel = viewModel
    }

    @ViewBuilder
    private func AddMenu() -> some View {
        Menu {
        } label: {
            Label("Add Entry", systemImage: "plus")
        } primaryAction: {
            viewModel.isAddEntryShown = true
        }
    }

    @Observable
    public class ViewModel {
        var isAddEntryShown: Bool = false

        var selectedTab: Tab = .Exercises

        public init() {
        }

        enum Tab {
            case Exercises
            case Types
            case Test
        }

        public protocol Delegate {
        }
    }
}

public protocol HomePageViewModelFactory {
    func create() -> HomePage.ViewModel
}

internal class PreviewHomePageViewModelFactory: HomePageViewModelFactory {
    func create() -> HomePage.ViewModel {
        .init()
    }
}

public extension EnvironmentValues {
    @Entry var homePageViewModelFactory: any HomePageViewModelFactory = PreviewHomePageViewModelFactory()
}

#Preview {
    @Previewable @State var viewModel = PreviewHomePageViewModelFactory().create()


    HomePage(viewModel: viewModel).padding()
}
