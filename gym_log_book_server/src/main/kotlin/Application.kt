@file:OptIn(ExperimentalUuidApi::class)

package net.michael_bailey.gym_log_book.server

import com.auth0.jwt.JWTVerifier
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.job
import kotlinx.rpc.krpc.ktor.server.Krpc
import kotlinx.rpc.krpc.ktor.server.KrpcRoute
import kotlinx.rpc.krpc.ktor.server.rpc
import kotlinx.rpc.krpc.serialization.json.json
import net.michael_bailey.gym_log_book.server.authentication.scope.ViewerScope
import net.michael_bailey.gym_log_book.server.database.setupDatabase
import net.michael_bailey.gym_log_book.server.di.RpcScope
import net.michael_bailey.gym_log_book.shared.authentication.controller.LoginTokenController
import net.michael_bailey.gym_log_book.shared.authentication.controller.OldAuthenticationController
import net.michael_bailey.gym_log_book.shared.authentication.controller.ViewerContextDebuggerController
import net.michael_bailey.gym_log_book.shared.counter.controller.CounterController
import net.michael_bailey.gym_log_book.shared.exercise.controller.ExerciseEntryController
import net.michael_bailey.gym_log_book.shared.exercise.controller.ExerciseTypeController
import org.koin.core.annotation.KoinApplication
import org.koin.core.component.KoinScopeComponent
import org.koin.core.component.get
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named
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

	val verifier: JWTVerifier = this.koin().get()

	setupDatabase()

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

		rpc("/rpc") {
			rpcConfig { serialization { json() } }
			val scope = createScope()

			registerService<OldAuthenticationController> {
				scope.get()
			}

			registerService<CounterController> {
				scope.get()
			}

			registerService<LoginTokenController> { scope.get() }
		}

		authenticate {
			rpc("/rpc/authenticated") {
				rpcConfig { serialization { json() } }

				val scope = createScope()

				val vmScope = getKoin().createScope(
					scopeId = "connection-${Uuid.generateV4()}",
					qualifier = named<ViewerScope>(),
				)

				vmScope.linkTo(scope.scope)

				registerService<ExerciseTypeController> {
					vmScope.get()
				}
				registerService<ExerciseEntryController> {
					vmScope.get()
				}
				registerService<ViewerContextDebuggerController> {
					scope.get()
				}
			}
		}
	}

	println("Started application module")
}

fun KrpcRoute.createScope(): KoinScopeComponent {
//	val scope = getKoin().createScope(
//		scopeId = "connection-${Uuid.generateV4()}",
//		qualifier = named<ViewerScope>(),
//	)

	val scope = getKoin().get<RpcScope> {
		parametersOf(Uuid.generateV7())
	}

	scope.declare<ApplicationCall>(call)

	this.coroutineContext.job.invokeOnCompletion {
		scope.close()
	}

	return scope
}
