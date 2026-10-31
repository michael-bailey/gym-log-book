package net.michael_bailey.gym_log_book.client.di.scopes

import net.michael_bailey.gym_log_book.client.home.tabs.entry.IExerciseEntryTabViewModel
import net.michael_bailey.gym_log_book.client.home.tabs.type.IExerciseTypeTabViewModel
import org.koin.core.component.KoinScopeComponent
import org.koin.core.component.getOrCreateScope
import org.koin.core.component.inject
import org.koin.core.scope.Scope

class AuthenticatedScope : KoinScopeComponent {
	override val scope: Scope by getOrCreateScope()

	val exerciseEntryListViewModel by inject<IExerciseEntryTabViewModel>()
	val exerciseTypeTabViewModel by inject<IExerciseTypeTabViewModel>()
}
