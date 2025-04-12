package org.british_information_technologies.gym_log_book.repository

import org.british_information_technologies.gym_log_book.lib.AppNotificationManager
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Test
import org.mockito.Mockito.mock

class TimerRepositoryTest {

	companion object {
		lateinit var notificationManager: AppNotificationManager

		@JvmStatic
		@BeforeClass
		fun beforeAll() {
			notificationManager = mock()
		}
	}

	private lateinit var exerciseSetTimerRepository: ExerciseSetTimerRepository

	@Before
	fun beforeEach() {
		exerciseSetTimerRepository = ExerciseSetTimerRepository(notificationManager)
	}

	@Test
	fun testTimerFinishes() {

	}

}