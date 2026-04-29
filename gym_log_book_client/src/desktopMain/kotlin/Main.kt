package net.michael_bailey.gym_log_book.client

import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.window.application
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import net.michael_bailey.gym_log_book.client.counter.counterClientModule
import net.michael_bailey.gym_log_book.client.exercise.exerciseClientModule
import net.michael_bailey.gym_log_book.client.platform.platformModule
import net.michael_bailey.gym_log_book.client.window.developer.DeveloperToolWindow
import org.koin.core.context.startKoin

fun main() = application {
	val koin = startKoin {
		modules(
			platformModule,
			applicationModule,
			counterClientModule,
			exerciseClientModule,
		)
	}.koin

//	val viewModel: ApplicationViewModel = koinViewModel()
//
//	val counterClientService = koin.get<CounterClientService> {
//		parametersOf(CIO.create())
//	}

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
		DeveloperToolWindow()
	}
}
