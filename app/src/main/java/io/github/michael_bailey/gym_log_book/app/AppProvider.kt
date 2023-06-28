package io.github.michael_bailey.gym_log_book.app

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.github.michael_bailey.gym_log_book.extension.application.preferences
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
/**
 * This handles all stuff that can be fetched automatically.
 */
abstract class AbstractAppProvider {

//	@Binds
//	@Singleton
//	abstract fun provideDebugPreferences(value: DebugPreferencesManager): DebugPreferencesManager
//
//	@Binds
//	@Singleton
//	abstract fun provideNotificationManager(value: DebugPreferencesManager): AppNotificationManager
}

@Module
@InstallIn(SingletonComponent::class)
/**
 * this is fore more complex dependency fetching.
 */
class AppProvider {
	@Provides
	@Singleton
	fun providePreferences(@ApplicationContext context: Context): SharedPreferences =
		(context as Application).preferences()
}