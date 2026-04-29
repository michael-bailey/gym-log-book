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
	single {
		HttpClient(get<HttpClientEngine>()) {
			installKrpc()
		}
	}

	single {
		get<HttpClient>().rpc(ClientConfig.exerciseRpcUrl) {
			println("created rpc ")
			rpcConfig {
				serialization {
					json()
				}
			}
		}
	} bind RpcClient::class
}