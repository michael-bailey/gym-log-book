package net.michael_bailey.gym_log_book.client.util

import androidx.compose.runtime.*
import org.koin.compose.getKoin
import org.koin.core.scope.Scope
import java.util.UUID.randomUUID

val LocalKoinScope = compositionLocalOf<Scope?> { null }

@Composable
inline fun <reified T : Any> rememberKoinScope(): Scope {
	val koin = getKoin()
	// Stable scope ID so recompositions don't create duplicates
	val scope = remember { koin.createScope<T>(scopeId = T::class.qualifiedName!! + "@" + randomUUID()) }
	DisposableEffect(Unit) {
		onDispose { scope.close() }
	}
	return scope
}

@Composable
inline fun <reified T : Any> scopedInject(): T {
	val scope = LocalKoinScope.current ?: error("No KoinScope in composition tree")
	return remember { scope.get<T>() }
}

@Composable
inline fun <reified T : Any> KoinScope(
	crossinline content: @Composable () -> Unit
) {
	val scope = rememberKoinScope<T>()
	CompositionLocalProvider(LocalKoinScope provides scope) {
		content()
	}
}