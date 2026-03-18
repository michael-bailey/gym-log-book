package net.michael_bailey.gym_log_book.client.config

const val COUNTER_RPC_URL = "ws://localhost:8080/counter"
expect object CounterClientConfig {
	val counterRpcUrl: String
}
