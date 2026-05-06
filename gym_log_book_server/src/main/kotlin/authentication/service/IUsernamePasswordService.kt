package net.michael_bailey.gym_log_book.server.authentication.service

interface IUsernamePasswordService {
	fun validateUsernameAndPassword(username: String, password: String): Boolean
}