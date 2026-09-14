package net.michael_bailey.gym_log_book.client.platform

import androidx.compose.material3.CalendarLocale
import io.ktor.client.engine.*
import io.ktor.client.engine.darwin.*
import kotlinx.rpc.RpcClient
import kotlinx.rpc.withService
import net.michael_bailey.gym_log_book.client.di.scopes.AuthenticatedScope
import net.michael_bailey.gym_log_book.shared.authentication.controller.ViewerContextDebuggerController
import org.koin.core.module.Module
import org.koin.dsl.bind
import org.koin.dsl.module
import platform.Foundation.NSLocale
import platform.Foundation.currentLocale

actual val platformModule: Module = module {
	single { NSLocale.currentLocale } bind CalendarLocale::class

	single { Darwin.create() } bind HttpClientEngine::class

	scope<AuthenticatedScope> {
		scoped {
			val client = get<RpcClient>()
			client.withService<ViewerContextDebuggerController>()
		}
	}
}