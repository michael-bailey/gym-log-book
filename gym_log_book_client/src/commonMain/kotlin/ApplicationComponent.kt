package net.michael_bailey.gym_log_book.client

import org.koin.core.component.KoinComponent
import org.koin.core.component.get

object ApplicationComponent : KoinComponent {
	val appViewModel = get<ApplicationViewModel>()
}