package test.controller

import kotlinx.rpc.annotations.Rpc

@Rpc
interface TestController {
	suspend fun test(): String
}