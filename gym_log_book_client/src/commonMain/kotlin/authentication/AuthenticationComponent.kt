package net.michael_bailey.gym_log_book.client.authentication

import authentication.IAuthenticationComponent
import net.michael_bailey.gym_log_book.client.authentication.view_model.LoginPageViewModel
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

object AuthenticationComponent : KoinComponent, IAuthenticationComponent {

	override fun createLoginPageViewModel(): LoginPageViewModel = get<LoginPageViewModel>()

}