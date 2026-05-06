package net.michael_bailey.gym_log_book.client.platform

import io.ktor.client.engine.cio.*
import kotlinx.rpc.RpcClient
import kotlinx.rpc.withService
import net.michael_bailey.gym_log_book.client.ApplicationViewModel
import net.michael_bailey.gym_log_book.client.di.scopes.AuthenticatedScope
import net.michael_bailey.gym_log_book.client.di.scopes.LoginScope
import net.michael_bailey.gym_log_book.client.window.developer.DeveloperWindowViewModel
import net.michael_bailey.gym_log_book.client.window.developer.counter.DevCounterTabPageViewModel
import net.michael_bailey.gym_log_book.client.window.developer.entry.DevExerciseEntryTabPageViewModel
import net.michael_bailey.gym_log_book.client.window.developer.login.DevLoginViewModel
import net.michael_bailey.gym_log_book.client.window.developer.type.DevExerciseTypeTabPageViewModel
import net.michael_bailey.gym_log_book.client.window.home.ExerciseHomeWindowViewModel
import net.michael_bailey.gym_log_book.shared.authentication.controller.ViewerContextDebuggerController
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

	scope<AuthenticatedScope> {
		scopedOf(::DevExerciseTypeTabPageViewModel)
		scopedOf(::DevExerciseEntryTabPageViewModel)

		scoped {
			val client = get<RpcClient>()
			client.withService<ViewerContextDebuggerController>()
		}
	}

	scope<LoginScope> {
		scopedOf(::DevLoginViewModel)
	}
}
