package net.michael_bailey.gym_log_book.client

import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.window.application
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import net.michael_bailey.gym_log_book.client.authentication.authenticationClientModule
import net.michael_bailey.gym_log_book.client.counter.counterClientModule
import net.michael_bailey.gym_log_book.client.exercise.exerciseClientModule
import net.michael_bailey.gym_log_book.client.platform.platformModule
import net.michael_bailey.gym_log_book.client.window.developer.DeveloperToolWindow
import net.michael_bailey.gym_log_book.client.window.home.ExerciseLoginWindow
import org.koin.compose.koinInject
import org.koin.core.context.startKoin

fun main() = application {
	startKoin {
		modules(
			platformModule,
			applicationModule,
			authenticationClientModule,
			counterClientModule,
			exerciseClientModule,
		)
	}.koin

	val viewModelStoreOwner = remember {
		object : ViewModelStoreOwner {
			private val store = ViewModelStore()
			override val viewModelStore: ViewModelStore
				get() = store
		}
	}

	DisposableEffect(Unit) {
		onDispose {
			viewModelStoreOwner.viewModelStore.clear()
		}
	}

	CompositionLocalProvider(LocalViewModelStoreOwner provides viewModelStoreOwner) {

		val applicationViewModel = koinInject<ApplicationViewModel>()

		val isLoginWindowShown by applicationViewModel.isLoginWindowShown

		if (isLoginWindowShown)
			ExerciseLoginWindow()


		DeveloperToolWindow()
	}
}
