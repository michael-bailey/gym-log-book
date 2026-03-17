package net.michael_bailey.gym_log_book

import io.ktor.server.application.*
import io.ktor.server.routing.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.rpc.krpc.ktor.server.Krpc
import kotlinx.rpc.krpc.ktor.server.rpc
import kotlinx.rpc.krpc.serialization.json.json
import net.michael_bailey.gym_log_book.server.di.counterModule
import net.michael_bailey.gym_log_book.shared.counter.controller.CounterController
import org.koin.core.qualifier.named
import org.koin.dsl.module
import org.koin.ktor.ext.get
import org.koin.ktor.plugin.Koin

private val applicationScopeQualifier = named("applicationScope")

fun Application.module() {

	println("Starting application module")

	val applicationScope = CoroutineScope(SupervisorJob() + Dispatchers.Default)

	monitor.subscribe(ApplicationStopped) {
		applicationScope.cancel()
	}

	install(Koin) {
		modules(
			counterModule(applicationScope),
			module {
				single(applicationScopeQualifier) { applicationScope }
			},
		)
	}

	install(Krpc) {
		serialization {
			json()
		}
	}

	routing {
		rpc("/counter") {
			registerService<CounterController> {
				get()
			}
		}
	}

	println("Started application module")
}
