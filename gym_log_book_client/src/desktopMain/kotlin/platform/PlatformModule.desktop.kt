package net.michael_bailey.gym_log_book.client.platform

import io.ktor.client.engine.cio.*
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformModule: Module = module {
	single { CIO.create() }
}