package net.michael_bailey.gym_log_book.server

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.rpc.krpc.ktor.server.Krpc
import kotlinx.rpc.krpc.ktor.server.rpc
import kotlinx.rpc.krpc.serialization.json.json
import net.michael_bailey.gym_log_book.server.di.counterModule
import net.michael_bailey.gym_log_book.shared.counter.controller.CounterController
import org.koin.ktor.ext.get
import org.koin.ktor.plugin.Koin

fun Application.module() {
	val applicationScope = CoroutineScope(SupervisorJob() + Dispatchers.Default)

	install(Koin) {
		modules(counterModule(applicationScope))
	}

	install(Krpc) {
		serialization {
			json()
		}
	}

	routing {
		get("/healthz") {
			call.respondText("ok")
		}

		rpc("/rpc/counter") {
			registerService<CounterController> {
				get()
			}
		}
	}
}
