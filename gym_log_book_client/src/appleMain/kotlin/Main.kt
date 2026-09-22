package net.michael_bailey.gym_log_book.client

import net.michael_bailey.gym_log_book.client.authentication.authenticationClientModule
import net.michael_bailey.gym_log_book.client.counter.counterClientModule
import net.michael_bailey.gym_log_book.client.exercise.exerciseClientModule
import net.michael_bailey.gym_log_book.client.platform.platformModule
import org.koin.core.context.startKoin

fun initKoin() {
	startKoin {
		modules(
			platformModule,
			applicationModule,
			authenticationClientModule,
			counterClientModule,
			exerciseClientModule,
		)
	}
}


