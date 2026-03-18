package net.michael_bailey.gym_log_book.shared.test.controller

import kotlinx.rpc.annotations.Rpc

@Rpc
interface TestController {
	suspend fun test(): String
}
