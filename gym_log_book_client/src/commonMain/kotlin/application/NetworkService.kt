package net.michael_bailey.gym_log_book.client.application

class NetworkService(
	private val networkManager: NetworkManager,
) {

	val connectionStatus = networkManager.status

}