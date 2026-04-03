package net.michael_bailey.gym_log_book.server

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.rpc.krpc.ktor.server.Krpc
import kotlinx.rpc.krpc.ktor.server.rpc
import kotlinx.rpc.krpc.serialization.json.json
import net.michael_bailey.gym_log_book.shared.counter.controller.CounterController
import net.michael_bailey.gym_log_book.shared.exercise.controller.ExerciseTypeController
import org.koin.core.annotation.KoinApplication
import org.koin.ktor.ext.get
import org.koin.ktor.plugin.Koin
import org.koin.plugin.module.dsl.withConfiguration

@KoinApplication
class ApplicationContainer

fun Application.module() {
	println("Starting application module")

	val applicationScope = CoroutineScope(SupervisorJob() + Dispatchers.Default)

	monitor.subscribe(ApplicationStopped) {
		applicationScope.cancel()
	}

	install(Koin) {
		withConfiguration<ApplicationContainer>()
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

		rpc("/rpc") {
			registerService<ExerciseTypeController> {
				get()
			}
			registerService<CounterController> {
				get()
			}
		}
	}

	println("Started application module")
}
