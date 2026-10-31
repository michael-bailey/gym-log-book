package authentication

import net.michael_bailey.gym_log_book.client.authentication.view_model.LoginPageViewModel
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

object AuthenticationComponent : KoinComponent, IAuthenticationComponent {

	override fun createLoginPageViewModel() = get<LoginPageViewModel>()

}