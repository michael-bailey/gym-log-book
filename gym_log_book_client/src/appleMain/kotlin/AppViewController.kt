package net.michael_bailey.gym_log_book.client

import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.window.ComposeUIViewController
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import net.michael_bailey.gym_log_book.client.authentication.authenticationClientModule
import net.michael_bailey.gym_log_book.client.authentication.view.LoginPage
import net.michael_bailey.gym_log_book.client.counter.counterClientModule
import net.michael_bailey.gym_log_book.client.di.scopes.AuthenticatedScope
import net.michael_bailey.gym_log_book.client.exercise.exerciseClientModule
import net.michael_bailey.gym_log_book.client.home.HomePage
import net.michael_bailey.gym_log_book.client.platform.platformModule
import net.michael_bailey.gym_log_book.client.util.KoinScope
import net.michael_bailey.gym_log_book.client.util.rememberKoinScope
import org.koin.compose.koinInject
import org.koin.core.context.startKoin

fun create() = ComposeUIViewController {
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

		if (isLoginWindowShown) {
			LoginPage()
		} else {
			val scope = rememberKoinScope<AuthenticatedScope>()
			KoinScope(scope = scope) {
				HomePage()
			}
		}
	}
}