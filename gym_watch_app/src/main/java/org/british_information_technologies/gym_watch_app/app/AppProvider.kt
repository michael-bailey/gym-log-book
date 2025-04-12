package org.british_information_technologies.gym_watch_app.app

import android.app.Application
import android.content.ContentResolver
import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.github.michael_bailey.android_chat_kit.extension.application.preferences
import kotlinx.coroutines.CoroutineScope
import javax.inject.Singleton

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

	@Provides
	@Singleton
	fun provideContentResolver(@ApplicationContext context: Context): ContentResolver {
		return context.contentResolver
	}

	@Provides
	@Singleton
	fun providesApplicationScope(@ApplicationContext context: Context): CoroutineScope =
		(context as App).applicationScope
}