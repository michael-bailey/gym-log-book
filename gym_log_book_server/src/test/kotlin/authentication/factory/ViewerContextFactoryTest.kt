@file:OptIn(ExperimentalUuidApi::class)

package authentication.factory

import com.auth0.jwt.interfaces.Claim
import com.auth0.jwt.interfaces.Payload
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.mockk.every
import io.mockk.mockk
import net.michael_bailey.gym_log_book.server.authentication.config.JWTClaimNames.USERNAME_CLAIM_KEY
import net.michael_bailey.gym_log_book.server.authentication.factory.ViewerContextFactory
import net.michael_bailey.gym_log_book.server.authentication.factory.ViewerContextFactory.Companion.GUEST_USERNAME
import net.michael_bailey.gym_log_book.server.authentication.factory.ViewerContextFactory.Companion.guestId
import net.michael_bailey.gym_log_book.server.authentication.model.ViewerContext
import net.michael_bailey.gym_log_book.server.authentication.service.ITokenService
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class ViewerContextFactoryTest {

	private val call: ApplicationCall = mockk()
	private val tokenService: ITokenService = mockk()

	@Test
	fun `test ViewerContextFactory create viewerContext using payload from token`() {
		val claim: Claim = mockk()
		val payload: Payload = mockk()
		val principal: JWTPrincipal = mockk()

		every { claim.asString() } returns USERNAME
		every { payload.subject } returns userId.toString()
		every { payload.claims } returns mapOf(USERNAME_CLAIM_KEY to claim)
		every { principal.payload } returns payload
		every { call.principal<JWTPrincipal>() } returns principal

		val actual = ViewerContext(
			userId = userId,
			username = USERNAME
		)

		val factory = ViewerContextFactory(
			call = call,
			tokenService = tokenService
		)

		val expected = factory.create()

		assertEquals(expected = expected, actual = actual)

	}

	@Test
	fun `test ViewerContextFactory create guest viewerContext when somehow the username and userId is null`() {
		val payload: Payload = mockk()
		val principal: JWTPrincipal = mockk()

		every { payload.subject } returns null
		every { payload.claims } returns emptyMap<String, Claim>()
		every { principal.payload } returns payload
		every { call.principal<JWTPrincipal>() } returns principal

		val actual = ViewerContext(
			userId = guestId,
			username = GUEST_USERNAME
		)

		val factory = ViewerContextFactory(
			call = call,
			tokenService = tokenService
		)

		val expected = factory.create()

		assertEquals(expected = expected, actual = actual)

	}

	@Test
	fun `test ViewerContextFactory create ViewerContext with no token, produces guest viewer context`() {
		every { call.principal<JWTPrincipal>() } returns null

		val actual = ViewerContext(
			userId = guestId,
			username = GUEST_USERNAME
		)

		val factory = ViewerContextFactory(
			call = call,
			tokenService = tokenService
		)

		val expected = factory.create()

		assertEquals(expected = expected, actual = actual)

	}

	private companion object {

		const val USERNAME = "username"

		val userId = Uuid.random()

	}

}