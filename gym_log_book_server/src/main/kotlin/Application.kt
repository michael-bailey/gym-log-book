@file:OptIn(ExperimentalUuidApi::class)

package net.michael_bailey.gym_log_book.server

import com.auth0.jwt.JWTVerifier
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.coroutines.*
import kotlinx.rpc.krpc.ktor.server.Krpc
import kotlinx.rpc.krpc.ktor.server.KrpcRoute
import kotlinx.rpc.krpc.ktor.server.rpc
import kotlinx.rpc.krpc.serialization.json.json
import net.michael_bailey.gym_log_book.server.authentication.scope.ViewerScope
import net.michael_bailey.gym_log_book.shared.authentication.controller.AuthenticationController
import net.michael_bailey.gym_log_book.shared.counter.controller.CounterController
import net.michael_bailey.gym_log_book.shared.exercise.controller.ExerciseEntryController
import net.michael_bailey.gym_log_book.shared.exercise.controller.ExerciseTypeController
import org.koin.core.annotation.KoinApplication
import org.koin.core.qualifier.named
import org.koin.core.scope.Scope
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

	val applicationScope = CoroutineScope(SupervisorJob() + Dispatchers.Default)

	monitor.subscribe(ApplicationStopped) {
		applicationScope.cancel()
	}

	install(Koin) {
		withConfiguration<ApplicationContainer>()
	}

	val verifier: JWTVerifier = this.koin().get()

	install(Authentication) {
		jwt {
			realm = "Gym Log Book"
			verifier(verifier)
			validate { credential ->
				// Ensure the token has a valid subject (userId)
				if (credential.payload.subject != null) {
					JWTPrincipal(credential.payload)
				} else {
					null
				}
			}
		}
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

		rpc("/rpc/authentication") {
			val scope = createScope()
			registerService<AuthenticationController> {
				scope.get()
			}
		}

		authenticate {
			rpc("/rpc") {

				val scope = createScope()

				registerService<CounterController> {
					scope.get()
				}
				registerService<ExerciseTypeController> {
					scope.get()
				}
				registerService<ExerciseEntryController> {
					scope.get()
				}

				rpcConfig { serialization { json() } }

				this.coroutineContext.job.invokeOnCompletion {
					scope.close()
				}
			}
		}
	}

	println("Started application module")
}

fun KrpcRoute.createScope(): Scope {
	val scope = getKoin().createScope(
		scopeId = "connection-${Uuid.generateV4()}",
		qualifier = named<ViewerScope>(),
	)
	scope.declare(call)

	return scope
}
