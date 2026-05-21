package net.michael_bailey.gym_log_book.client.platform

import androidx.compose.material3.CalendarLocale
import io.ktor.client.engine.*
import io.ktor.client.engine.js.*
import org.koin.core.module.Module
import org.koin.dsl.bind
import org.koin.dsl.module

actual val platformModule: Module = module {
	single { Js.create() } bind HttpClientEngine::class

	single { CalendarLocale.current } bind CalendarLocale::class

}