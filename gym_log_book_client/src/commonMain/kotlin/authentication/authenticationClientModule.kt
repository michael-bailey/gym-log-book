package net.michael_bailey.gym_log_book.client.authentication

import net.michael_bailey.gym_log_book.client.application.NetworkManager
import net.michael_bailey.gym_log_book.client.authentication.repository.AuthenticationStorageRepository
import net.michael_bailey.gym_log_book.client.authentication.repository.LoginRepository
import net.michael_bailey.gym_log_book.client.authentication.repository.NewAuthenticationRepository
import net.michael_bailey.gym_log_book.client.authentication.service.AuthenticationLoginService
import net.michael_bailey.gym_log_book.client.authentication.service.AuthenticationService
import net.michael_bailey.gym_log_book.client.authentication.service.OldAuthenticationService
import net.michael_bailey.gym_log_book.client.authentication.view_model.LoginPageViewModel
import net.michael_bailey.gym_log_book.client.net.michael_bailey.gym_log_book.client.authentication.repository.IAuthenticationRepository
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val authenticationClientModule = module {

	single {
		val networkManager = get<NetworkManager>()
		NewAuthenticationRepository(
			controller = networkManager.getService(),
			coroutineScope = get()
		)
	}

	single {
		val networkManager = get<NetworkManager>()
		LoginRepository(
			controller = networkManager.getService(),
			coroutineScope = get()
		)
	}

	singleOf(::OldAuthenticationService)
	singleOf(::AuthenticationLoginService)
	singleOf(::AuthenticationStorageRepository) bind IAuthenticationRepository::class


	singleOf(::AuthenticationService)

	viewModelOf(::LoginPageViewModel)
}
