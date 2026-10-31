package net.michael_bailey.gym_log_book.client.window.developer.network

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.map
import net.michael_bailey.gym_log_book.client.application.NetworkManager
import net.michael_bailey.gym_log_book.client.application.NetworkService

class NetworkPageViewModel(
	private val networkService: NetworkService,
) : ViewModel() {

	val statusText = networkService.connectionStatus.map(::statusToText)

	private fun statusToText(
		status: NetworkManager.Status,
	): String = when (status) {
		NetworkManager.Status.Connected -> "Connected"
		NetworkManager.Status.Connecting -> "Connecting"
		NetworkManager.Status.Disconnected -> "Disconnected"
	}

}