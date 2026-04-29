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
		HttpClient(get<HttpClientEngine>()) {
			installKrpc()
		}
	}

	single {

		println("creating clientConfig")
		val clientConfig = get<ClientConfig>()
		println("created clientConfig")

		println("creating client")
		val client = get<HttpClient>()
		println("created client")

		println("creating rpc")
		val rpc = client.rpc(clientConfig.unauthenticatedUrl.toString()) {
			println("initing rpc")
			rpcConfig {
				serialization {
					json()
				}
			}
			println("inited rpc")
		}
		println("created rpc")

		rpc
	} bind RpcClient::class
}