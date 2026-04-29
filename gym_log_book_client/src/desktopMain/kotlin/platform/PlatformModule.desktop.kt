package net.michael_bailey.gym_log_book.client.platform

import io.ktor.client.engine.cio.*
import net.michael_bailey.gym_log_book.client.ApplicationViewModel
import net.michael_bailey.gym_log_book.client.di.AuthenticatedScope
import net.michael_bailey.gym_log_book.client.di.AuthenticationScope
import net.michael_bailey.gym_log_book.client.window.ExerciseHomeWindowViewModel
import net.michael_bailey.gym_log_book.client.window.developer.DeveloperWindowViewModel
import net.michael_bailey.gym_log_book.client.window.developer.counter.DevCounterTabPageViewModel
import net.michael_bailey.gym_log_book.client.window.developer.entry.DevExerciseEntryTabPageViewModel
import net.michael_bailey.gym_log_book.client.window.developer.type.DevExerciseTypeTabPageViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.scopedOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

actual val platformModule: Module = module {
	single { CIO.create() }

	viewModelOf(::ExerciseHomeWindowViewModel)
	viewModelOf(::ApplicationViewModel)
	viewModelOf(::DeveloperWindowViewModel)

	viewModelOf(::DevCounterTabPageViewModel)

	scope<AuthenticationScope> { }

	scope<AuthenticatedScope> {
		scopedOf(::DevExerciseTypeTabPageViewModel)
		scopedOf(::DevExerciseEntryTabPageViewModel)
	}
}