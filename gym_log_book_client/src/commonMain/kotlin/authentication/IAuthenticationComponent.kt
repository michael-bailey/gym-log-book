package authentication

import net.michael_bailey.gym_log_book.client.authentication.view_model.ILoginPageViewModel

interface IAuthenticationComponent {
	fun createLoginPageViewModel(): ILoginPageViewModel
}