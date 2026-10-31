import SwiftUI
import Observation

import Swinject

import iosComponents
import gym_client_kt

@main struct MyApp: App {
	
	private let container: Container
	
	var body: some Scene {
		WindowGroup {
			ContentView(viewModel: self.container.resolve(ContentView.ViewModel.self)!)
				.environment(\.container, self.container)
		}
	}
	
	init() {
		MainKt.doInitKoin()
		
		self.container = .init()
		
		let assembler = Assembler(container: self.container)
		assembler.apply(assemblies: [
			ApplicationAssembly(),
			LoginAssembly(),
			HomeAssembly(),
		])
	}
}
