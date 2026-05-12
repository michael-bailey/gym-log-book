package net.michael_bailey.gym_log_book.client.authentication

import io.ktor.client.*
import io.ktor.client.engine.*
import io.ktor.client.plugins.auth.*
import io.ktor.client.plugins.auth.providers.*
import kotlinx.coroutines.flow.first
import kotlinx.rpc.RpcClient
import kotlinx.rpc.krpc.ktor.client.installKrpc
import kotlinx.rpc.krpc.ktor.client.rpc
import kotlinx.rpc.krpc.ktor.client.rpcConfig
import kotlinx.rpc.krpc.serialization.json.json
import kotlinx.rpc.withService
import net.michael_bailey.gym_log_book.client.authentication.repository.AuthenticationRepository
import net.michael_bailey.gym_log_book.client.authentication.service.AuthenticationLoginService
import net.michael_bailey.gym_log_book.client.authentication.service.AuthenticationService
import net.michael_bailey.gym_log_book.client.config.ClientConfig
import net.michael_bailey.gym_log_book.client.di.scopes.AuthenticatedScope
import net.michael_bailey.gym_log_book.client.di.scopes.LoginScope
import net.michael_bailey.gym_log_book.shared.authentication.controller.AuthenticationController
import org.koin.core.module.dsl.scopedOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val authenticationClientModule = module {

	singleOf(::AuthenticationService)
	singleOf(::AuthenticationRepository)

	single {
		println("creating exercise RCP")
		get<RpcClient>().withService<AuthenticationController>()
	} bind AuthenticationController::class

	scope<LoginScope> {
		scopedOf(::AuthenticationLoginService)
	}

	scope<AuthenticatedScope> {
		scoped {
			println("Creating application level http Client")
			HttpClient(get<HttpClientEngine>()) {

				val authenticationService = get<AuthenticationService>()

				install(Auth) {
					bearer {
						loadTokens {
							val token = authenticationService.token.first() ?: return@loadTokens null
							BearerTokens(token.accessToken, token.refreshToken)
						}
						refreshTokens {
							val tokens = authenticationService.refreshTokens()
							tokens?.let { BearerTokens(it.accessToken, it.refreshToken) }
						}
					}
				}

				installKrpc()
			}
		}
		scoped {
			val clientConfig = get<ClientConfig>()
			val client = get<HttpClient>()
			client.rpc(clientConfig.authenticatedUrl.toString()) {
				rpcConfig {
					serialization {
						json()
					}
				}
			}
		} bind RpcClient::class
	}
}
