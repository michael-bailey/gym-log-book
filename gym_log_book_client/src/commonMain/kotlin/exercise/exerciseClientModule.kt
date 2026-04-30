package net.michael_bailey.gym_log_book.client.exercise

import io.ktor.client.*
import io.ktor.client.engine.*
import kotlinx.rpc.RpcClient
import kotlinx.rpc.krpc.ktor.client.installKrpc
import kotlinx.rpc.krpc.ktor.client.rpc
import kotlinx.rpc.krpc.ktor.client.rpcConfig
import kotlinx.rpc.krpc.serialization.json.json
import kotlinx.rpc.withService
import net.michael_bailey.gym_log_book.client.config.ClientConfig
import net.michael_bailey.gym_log_book.client.di.AuthenticatedScope
import net.michael_bailey.gym_log_book.client.exercise.service.ExerciseEntryService
import net.michael_bailey.gym_log_book.client.exercise.service.ExerciseTypeService
import net.michael_bailey.gym_log_book.client.exercise.view_model.ExerciseTypeListViewModel
import net.michael_bailey.gym_log_book.client.home.HomePageViewModel
import net.michael_bailey.gym_log_book.client.home.tabs.IExerciseEntryTabViewViewModel
import net.michael_bailey.gym_log_book.client.home.tabs.entry.ExerciseEntryTabViewViewModel
import net.michael_bailey.gym_log_book.shared.exercise.controller.ExerciseEntryController
import net.michael_bailey.gym_log_book.shared.exercise.controller.ExerciseTypeController
import org.koin.core.module.dsl.scopedOf
import org.koin.dsl.bind
import org.koin.dsl.module

val exerciseClientModule = module {

	scope<AuthenticatedScope> {

		scoped {
			println("Creating application level http Client")
			HttpClient(get<HttpClientEngine>()) {
				installKrpc()
			}
		}

		scoped {

		val clientConfig = get<ClientConfig>()
			val client = get<HttpClient>()
			client.rpc(clientConfig.unauthenticatedUrl.toString()) {
				rpcConfig {
					serialization {
						json()
					}
				}
			}
		} bind RpcClient::class

		scoped {
			println("creating exercise RCP")
			get<RpcClient>().withService<ExerciseTypeController>()
		} bind ExerciseTypeController::class

		scoped {
			println("creating exercise RCP")
			get<RpcClient>().withService<ExerciseEntryController>()
		} bind ExerciseEntryController::class

		scopedOf(::ExerciseEntryService)
		scopedOf(::ExerciseTypeService)

		scopedOf(::ExerciseTypeListViewModel)
		scopedOf(::ExerciseEntryTabViewViewModel) bind IExerciseEntryTabViewViewModel::class
		scopedOf(::HomePageViewModel)
	}

}