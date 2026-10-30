@file:OptIn(ExperimentalUuidApi::class)

package net.michael_bailey.gym_log_book.server

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.job
import kotlinx.rpc.krpc.ktor.server.Krpc
import kotlinx.rpc.krpc.ktor.server.KrpcRoute
import kotlinx.rpc.krpc.ktor.server.rpc
import kotlinx.rpc.krpc.serialization.json.json
import net.michael_bailey.gym_log_book.server.database.setupDatabase
import net.michael_bailey.gym_log_book.server.di.RpcScope
import net.michael_bailey.gym_log_book.shared.application.HeartbeatController
import net.michael_bailey.gym_log_book.shared.authentication.controller.AuthenticationController
import net.michael_bailey.gym_log_book.shared.authentication.controller.LoginTokenController
import net.michael_bailey.gym_log_book.shared.authentication.controller.NewAuthenticationController
import net.michael_bailey.gym_log_book.shared.counter.controller.CounterController
import net.michael_bailey.gym_log_book.shared.exercise.controller.ExerciseEntryController
import net.michael_bailey.gym_log_book.shared.exercise.controller.ExerciseTypeController
import org.koin.core.annotation.KoinApplication
import org.koin.core.component.KoinScopeComponent
import org.koin.core.component.get
import org.koin.core.parameter.parametersOf
import org.koin.ktor.plugin.Koin
import org.koin.ktor.plugin.koin
import org.koin.mp.KoinPlatform.getKoin
import org.koin.plugin.module.dsl.withConfiguration
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@KoinApplication
class ApplicationContainer

fun Application.module() {
	println("Starting application module")

	install(Koin) {
		withConfiguration<ApplicationContainer>()
	}

	val applicationScope: CoroutineScope = koin().get()

	monitor.subscribe(ApplicationStopped) {
		applicationScope.cancel()
	}

	setupDatabase()

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

			println("Got new rpc session")

			rpcConfig { serialization { json() } }
			val scope = createScope()

			registerService<AuthenticationController> {
				print("Creating OldAuthenticationController")
				scope.get()
			}

			registerService<CounterController> {
				scope.get()
			}

			registerService<LoginTokenController> { scope.get() }
			registerService<HeartbeatController> { scope.get() }

			registerService<NewAuthenticationController> { scope.get() }

			registerService<ExerciseEntryController> { scope.get() }
			registerService<ExerciseTypeController> { scope.get() }

		}
	}

	println("Started application module")
}

fun KrpcRoute.createScope(): KoinScopeComponent {
	val scope = getKoin().get<RpcScope> {
		parametersOf(Uuid.generateV7())
	}

	scope.declare<ApplicationCall>(call)

	this.coroutineContext.job.invokeOnCompletion {
		scope.close()
	}

	return scope
}
