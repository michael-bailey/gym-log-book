package io.github.michael_bailey.gym_log_book.data_manager

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.github.michael_bailey.gym_log_book.table.ExerciseTable
import io.github.michael_bailey.gym_log_book.table.ExerciseTypeTable
import io.github.michael_bailey.gym_log_book.table.WeightTable
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataManagerProvider {

	@Provides
	@Singleton
	fun provideExerciseTypeDataManager(
		@ApplicationContext context: Context,
		table: ExerciseTypeTable
	): ExerciseTypeDataManager {
		return ExerciseTypeDataManager(context, table)
	}

	@Provides
	@Singleton
	fun provideExerciseDataManager(
		table: ExerciseTable
	): ExerciseDataManager {
		return ExerciseDataManager(table)
	}

	@Provides
	@Singleton
	fun provideWeightDataManager(
		@ApplicationContext context: Context,
		table: WeightTable
	): WeightDataManager {
		return WeightDataManager(context, table)
	}
}