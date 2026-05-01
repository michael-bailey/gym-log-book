package net.michael_bailey.gym_log_book.client.authentication

import io.ktor.client.*
import io.ktor.client.engine.*
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
import net.michael_bailey.gym_log_book.client.di.AuthenticationScope
import net.michael_bailey.gym_log_book.shared.authentication.controller.AuthenticationController
import org.koin.core.module.dsl.scopedOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val authenticationClientModule = module {

	singleOf(::AuthenticationService)
	singleOf(::AuthenticationRepository)

	scope<AuthenticationScope> {
		scoped {
			println("Creating application level http Client")
			HttpClient(get<HttpClientEngine>()) {
				installKrpc()
			}
		}

		scoped {
			val clientConfig = get<ClientConfig>()
			val client = get<HttpClient>()
			client.rpc(clientConfig.authenticationUrl.toString()) {
				rpcConfig {
					serialization {
						json()
					}
				}
			}
		} bind RpcClient::class

		scoped {
			println("creating exercise RCP")
			get<RpcClient>().withService<AuthenticationController>()
		} bind AuthenticationController::class

		scopedOf(::AuthenticationLoginService)
	}
}