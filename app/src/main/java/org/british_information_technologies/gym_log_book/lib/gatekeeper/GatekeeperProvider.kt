package org.british_information_technologies.gym_log_book.lib.gatekeeper

import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
/**
 * This class ensures there is only one instance of a gatekeeper injected in a component
 */
class GatekeeperProvider {
	@Provides
	@Singleton
	fun provideGatekeeper(
		preference: SharedPreferences
	): Gatekeeper {
		return Gatekeeper(preference)
	}
}