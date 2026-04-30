package net.michael_bailey.gym_log_book.client

import io.ktor.client.*
import io.ktor.client.engine.*
import kotlinx.rpc.RpcClient
import kotlinx.rpc.krpc.ktor.client.installKrpc
import kotlinx.rpc.krpc.ktor.client.rpc
import kotlinx.rpc.krpc.ktor.client.rpcConfig
import kotlinx.rpc.krpc.serialization.json.json
import net.michael_bailey.gym_log_book.client.config.ClientConfig
import org.koin.dsl.bind
import org.koin.dsl.module

val applicationModule = module {

	single { ClientConfig() }

	single {
		println("Creating application level http Client")
		HttpClient(get<HttpClientEngine>()) {
			installKrpc()
		}
	}

	single {
		val clientConfig = get<ClientConfig>()
		val client = get<HttpClient>()
		val rpc = client.rpc(clientConfig.unauthenticatedUrl.toString()) {
			rpcConfig {
				serialization { json() }
			}
		}
		rpc
	} bind RpcClient::class
}