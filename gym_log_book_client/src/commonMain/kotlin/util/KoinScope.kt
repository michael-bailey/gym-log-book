@file:OptIn(kotlin.uuid.ExperimentalUuidApi::class)

package net.michael_bailey.gym_log_book.client.util

import androidx.compose.runtime.*
import org.koin.compose.getKoin
import org.koin.core.scope.Scope
import kotlin.uuid.Uuid

val LocalKoinScope = compositionLocalOf<Scope?> { null }

@Composable
inline fun <reified T : Any> rememberKoinScope(): Scope {
	val koin = getKoin()
	val scope = remember {
		koin.createScope<T>(scopeId = "${T::class}-${Uuid.random()}")
	}

	DisposableEffect(scope) {
		onDispose { scope.close() }
	}

	return scope
}

@Composable
inline fun <reified T : Any> scopedInject(): T {
	val scope = LocalKoinScope.current ?: error("No KoinScope in composition tree")
	return remember(scope) { scope.get<T>() }
}

@Composable
fun KoinScope(
	scope: Scope,
	content: @Composable () -> Unit,
) {
	CompositionLocalProvider(LocalKoinScope provides scope) {
		content()
	}
}

@Composable
inline fun <reified T : Any> KoinScope(
	crossinline content: @Composable () -> Unit,
) {
	val scope = rememberKoinScope<T>()
	KoinScope(scope = scope) {
		content()
	}
}
