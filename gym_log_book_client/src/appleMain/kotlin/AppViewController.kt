package net.michael_bailey.gym_log_book.client

import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.window.ComposeUIViewController
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import net.michael_bailey.gym_log_book.client.authentication.authenticationClientModule
import net.michael_bailey.gym_log_book.client.authentication.view.LoginPage
import net.michael_bailey.gym_log_book.client.counter.counterClientModule
import net.michael_bailey.gym_log_book.client.exercise.exerciseClientModule
import net.michael_bailey.gym_log_book.client.platform.platformModule
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
		LoginPage()
	}
}