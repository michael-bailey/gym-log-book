package org.british_information_technologies.gym_log_book.database

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import org.british_information_technologies.gym_log_book.database.dao.ExerciseEntryDao
import org.british_information_technologies.gym_log_book.database.dao.ExerciseGroupDao
import org.british_information_technologies.gym_log_book.database.dao.ExerciseTypeDao
import org.british_information_technologies.gym_log_book.database.dao.TaskDao
import org.british_information_technologies.gym_log_book.database.dao.WeightEntryDao
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
		).build()
	}

	@Provides
	@Singleton
	fun provideInternalDatabase(
		@ApplicationContext context: Context
	): InternalDatabase {
		return Room.databaseBuilder(
			context,
			InternalDatabase::class.java,
			"internal_database"
		).build()
	}

	@Provides
	fun provideExerciseTypeDao(
		appDatabase: AppDatabase,
	): ExerciseTypeDao {
		return appDatabase.exerciseTypeDao()
	}

	@Provides
	fun provideExerciseEntryDao(
		appDatabase: AppDatabase,
	): ExerciseEntryDao {
		return appDatabase.exerciseEntryDao()
	}

	@Provides
	fun provideWeightEntryDao(
		appDatabase: AppDatabase,
	): WeightEntryDao {
		return appDatabase.weightEntryDao()
	}

	@Provides
	fun provideExerciseGroupDao(
		appDatabase: AppDatabase,
	): ExerciseGroupDao = appDatabase.exerciseGroupDao()

	@Provides
	fun provideTaskDao(
		internalDatabase: InternalDatabase,
	): TaskDao {
		return internalDatabase.taskDao()
	}
}