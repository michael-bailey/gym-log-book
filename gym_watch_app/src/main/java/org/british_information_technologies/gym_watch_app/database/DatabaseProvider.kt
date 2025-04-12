package org.british_information_technologies.gym_watch_app.database

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseProvider {

	@Provides
	@Singleton
	fun provideAppDatabase(
		@ApplicationContext context: Context,
	): AppDatabase {
		return Room.databaseBuilder(
			context,
			AppDatabase::class.java,
			"app_database"
		).fallbackToDestructiveMigration().build()
	}
}